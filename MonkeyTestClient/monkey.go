package main

import (
    "github.com/y0ssar1an/q"
    "net"
    "fmt"
    "time"
    "encoding/binary"
    "math/rand"
    "sync"
    "os"
    "strconv"
)

const (
    M_PRE_GAME = 0
    M_CATEGORY = 1
    M_SELECTOR = 2
    M_QUESTION = 3
    M_BUZZED   = 4
    M_SCREWED  = 5

    A_NOTHING  = 0
    A_GENERAL  = 1
    A_CATEGORY = 2
    A_BUZZ     = 3
    A_SCREW    = 4
    A_ANSWER   = 5
)

func CreateSignOnMessage(alias string) Message {
    return CreateMessage(PRE_GAME, SIGN_ON, []byte(alias))
}

func CreatePlayerReadyMessage() Message {
    return CreateMessage(PRE_GAME, PLAYER_READY, nil)
}

func CreateGeneralTextMessage(text string) Message {
    return CreateMessage(GENERAL, GENERAL_TEXT, []byte(text))
}

func CreateCategorySelectionMessage(category int) Message {
    tmp_int := make([]byte, 4)
    binary.BigEndian.PutUint32(tmp_int, uint32(category))
    return CreateMessage(IN_GAME, CATEGORY_SELECTION, tmp_int)
}

func CreateScrewMessage(screw int) Message {
    tmp_int := make([]byte, 4)
    binary.BigEndian.PutUint32(tmp_int, uint32(screw))
    return CreateMessage(IN_GAME, SCREW, tmp_int)
}

func CreateAnswerMessage(answer int) Message {
    tmp_int := make([]byte, 4)
    binary.BigEndian.PutUint32(tmp_int, uint32(answer))
    return CreateMessage(IN_GAME, ANSWER, tmp_int)
}

type Monkey struct {
    Id int
    Alias string
    Answers int
    Categories int
    Timeout int64
    State int
    Players []int
}

func (m *Monkey) run(id int, addr string, wg *sync.WaitGroup) {
    m.State = PRE_GAME
    fmt.Printf("[Monkey-%2v] Connecting Monkey to %v\n", id, addr)
    conn, err := net.Dial("tcp", addr)
    if err != nil {
        fmt.Printf("[Monkey-%2v] Could not connect\n", id)
        wg.Done()
        return
    }
    server := Client{}
    server.Conn = conn
    server.Alias = "Server"
    time.Sleep(1 * time.Second)
    fmt.Printf("[Monkey-%2v] Sign on\n", id)
    server.SendMessage(CreateSignOnMessage("monkey"))
    //msg := CreateGeneralTextMessage("monkey says hi")
    for {
        var actions []int
        recv, err := server.ReadMessage()
        if err != nil {
            fmt.Printf("[Monkey-%2v] Connection died :(\n", id)
            break
        }
        q.Q(recv)

        if recv.Group == POST_GAME && recv.Type == GAME_END {
            time.Sleep(1 * time.Second)
            server.SendMessage(CreatePlayerReadyMessage())
            fmt.Printf("[Monkey-%2v] Ready\n", id)
        }

        if recv.Group == GENERAL && recv.Type == GENERAL_TEXT {
            fmt.Printf("[Monkey-%2v] General text: %s\n", id, string(recv.Payload))
            continue
        }

        if recv.Group == GENERAL && recv.Type == PLAYER_LIST {
            offset := int(binary.BigEndian.Uint32(recv.Payload[0:4]))
            nb_players := offset / 4
            m.Players = nil
            for i := 0; i < nb_players; i++ {
                offset = int(binary.BigEndian.Uint32(recv.Payload[i*4:i*4+4]))
                id := int(binary.BigEndian.Uint32(recv.Payload[offset:offset+4]))
                ready := recv.Payload[offset+4]
                if ready == READY {
                    m.Players = append(m.Players, id)
                }
                //fmt.Printf("[Monkey-%2v] %v players ready\n", id,  len(m.Players))
            }
        }
        if recv.Group == IN_GAME && recv.Type == CATEGORY_SELECTOR_ANNOUNCEMENT {
            m.Timeout = int64(binary.BigEndian.Uint64(recv.Payload[0:8]))
            selector := int(binary.BigEndian.Uint32(recv.Payload[8:12]))
            m.Categories = int(binary.BigEndian.Uint32(recv.Payload[12:16]))
            if m.Id == selector {
                m.State = M_CATEGORY
                actions = []int{A_CATEGORY}
                fmt.Printf("[Monkey-%2v] I am category selector\n", id)
            } else {
                m.State = M_CATEGORY
                actions = []int{A_NOTHING, A_GENERAL}
            }
        }

        if recv.Group == IN_GAME && recv.Type == SCOREBOARD {
            fmt.Printf("[Monkey-%2v] Received scoreboard\n", id)
        }

        if recv.Group == GENERAL && recv.Type == PLAYER_LIST {
            fmt.Printf("[Monkey-%2v] Received player list\n", id)
        }

        if recv.Group == GENERAL && recv.Type == GENERAL_TEXT {
            fmt.Printf("[Monkey-%2v] Received general text: %v\n", id, string(recv.Payload))
        }

        if recv.Group == POST_GAME && recv.Type == GAME_END {
            m.State = M_PRE_GAME
        }

        switch (m.State) {
            case M_PRE_GAME:
                if recv.Group != PRE_GAME {
                    break
                }
                if recv.Type == SIGN_ON_RESPONSE {
                    m.Id = int(binary.BigEndian.Uint32(recv.Payload[0:4]))
                    m.Alias = string(recv.Payload[4:])
                    fmt.Printf("[Monkey-%2v] Got my id and alias: %v %v\n", id, m.Id, m.Alias)
                    server.SendMessage(CreatePlayerReadyMessage())
                    fmt.Printf("[Monkey-%2v] Ready\n", id)
                }
                actions = []int{A_NOTHING, A_GENERAL}
            case M_CATEGORY:
                if recv.Group != IN_GAME {
                    break
                }

                if recv.Type == QUESTION {
                    fmt.Printf("[Monkey-%2v] Received Question\n", id)
                    m.Answers = int(binary.BigEndian.Uint32(recv.Payload[12:16]))
                    m.Timeout = int64(binary.BigEndian.Uint64(recv.Payload[16:24]))
                    m.State = M_QUESTION
                    actions = []int{A_NOTHING, A_GENERAL, A_BUZZ, A_SCREW}
                }
            case M_QUESTION:
                if recv.Group != IN_GAME || recv.Type != BUZZ_RESULT && recv.Type != SCREW_RESULT {
                    break
                }
                var answerer int
                if recv.Type == BUZZ_RESULT {
                    answerer = int(binary.BigEndian.Uint32(recv.Payload[:4]))
                } else {
                    answerer = int(binary.BigEndian.Uint32(recv.Payload[4:8]))
                }

                if m.Id == answerer {
                    if recv.Type == BUZZ_RESULT {
                        m.Timeout = int64(binary.BigEndian.Uint64(recv.Payload[4:12]))
                        fmt.Printf("[Monkey-%2v] I buzzed and get to answer\n", id)
                    } else {
                        m.Timeout = int64(binary.BigEndian.Uint64(recv.Payload[8:16]))
                        fmt.Printf("[Monkey-%2v] I got screwed and have to answer\n", id)
                    }
                    actions = []int{A_ANSWER}
                    break
                }

                actions = []int{A_NOTHING, A_GENERAL}
        }


        if len(actions) == 0 {
            continue
        }

        m.Timeout -= 1000
        action := actions[rand.Intn(len(actions))]

        switch (action) {
            case A_NOTHING:
                break
            case A_GENERAL:
                if rand.Float32() < 0.05 {
                    linus := quotes[rand.Intn(len(quotes))]
                    // only in 5% of cases actually send general message
                    fmt.Printf("[Monkey-%2v] Sending general message\n", id)
                    server.SendMessage(CreateGeneralTextMessage(linus))
                }
            case A_CATEGORY:
                if m.Timeout > 0 {
                    time.Sleep(time.Duration(rand.Intn(int(m.Timeout))) * time.Millisecond)
                }
                fmt.Printf("[Monkey-%2v] Selecting category\n", id)
                server.SendMessage(CreateCategorySelectionMessage(rand.Intn(m.Categories)))
            case A_BUZZ:
                if m.Timeout > 0 {
                    time.Sleep(time.Duration(rand.Intn(int(m.Timeout))) * time.Millisecond)
                }
                fmt.Printf("[Monkey-%2v] Sending buzz\n", id)
                server.SendMessage(CreateMessage(IN_GAME, BUZZ, nil))
            case A_SCREW:
                if m.Timeout > 0 {
                    time.Sleep(time.Duration(rand.Intn(int(m.Timeout))) * time.Millisecond)
                }
                screw := rand.Intn(len(m.Players))
                fmt.Printf("[Monkey-%2v] Sending screw to %v\n", id, screw)
                server.SendMessage(CreateScrewMessage(m.Players[screw]))
            case A_ANSWER:
                if m.Timeout > 0 {
                    time.Sleep(time.Duration(rand.Intn(int(m.Timeout))) * time.Millisecond)
                }
                fmt.Printf("[Monkey-%2v] Sending answer\n", id)
                server.SendMessage(CreateAnswerMessage(rand.Intn(m.Answers)))
        }
    }
    wg.Done()
}

func main() {

    var wg sync.WaitGroup
    nb_monkeys := 4
    addr := "localhost:8081"
    if len(os.Args) > 2 {
        addr = os.Args[1]
        arg, err := strconv.Atoi(os.Args[2])
        if err == nil && arg > 0 {
            nb_monkeys = arg
        }
    }

    for i := 0; i < nb_monkeys; i++ {
        wg.Add(1)
        m := Monkey{}
        go m.run(i, addr, &wg)
    }

    wg.Wait()

}
