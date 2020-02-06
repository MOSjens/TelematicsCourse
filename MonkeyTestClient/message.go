package main

import (
    "encoding/binary"
    "net"
    "errors"
    "github.com/y0ssar1an/q"
    "fmt"
    "math/rand"
)

var HeaderReadError = errors.New("Header read incorrect length")
var PayloadReadError = errors.New("Incorrect read on payload")
var WriteError = errors.New("Parser error")

type Question struct {
    Category string `json:"category"`
    //Type string `json:"type"`
    Difficulty string `json:"difficulty"`
    Question string `json:"question"`
    CorrectAnswer string `json:"correct_answer"`
    IncorrectAnswers []string `json:"incorrect_answers"`
}

type Client struct {
    Id int
    Alias string
    Score int
    Ready int
    Screws int
    Conn net.Conn
}

func (c *Client) Close() {
    c.Conn.Close()
}

type Message struct {
    Version int
    Group int
    Type int
    Length int
    Payload []byte
    Sender *Client
}

func CreatePlayerListMessage(clients map[int]*Client) Message {
    payload := make([]byte, 4 * len(clients))
    num := 0
    temp_int := make([]byte, 4)
    for id := range clients {
        offset := len(payload)
        binary.BigEndian.PutUint32(temp_int, uint32(offset))
        for i := 0; i < 4; i++ {
            payload[num * 4 + i] = temp_int[i]
        }
        binary.BigEndian.PutUint32(temp_int, uint32(id))
        payload = append(payload, temp_int...)
        payload = append(payload, byte(clients[id].Ready))
        payload = append(payload, []byte(clients[id].Alias)...)
        num++
    }
    return CreateMessage(GENERAL, PLAYER_LIST, payload)
}

func CreateCategorySelectorMessage(questions []*Question, player_id int) Message {
    /*
     + --------------------------------------------------------*---+
     P                      Timeout_Millis* (8)                  P
     +-----------------------------------------------------------+
     P          Player ID      (4)   |      Num_Categories (4)   P
     +-----------------------------------------------------------+
     P     Difficulty_Offset_0 (4)   |   Category Offset_0 (4)   P
     +-----------------------------------------------------------+
     P              ...              |           ...             P
     +-----------------------------------------------------------+
     P     Difficulty_Offset_n (4)   |   Category Offset_n (4)   P
     +-----------------------------------------------------------+
     P           Difficulty_0        |        Category_0         P
     +-----------------------------------------------------------+
     P                ...            |             ...           P
     +-----------------------------------------------------------+
     P           Difficulty_n        |        Category_n         P
     +-----------------------------------------------------------+
     */

    timeout := make([]byte, 8)
    binary.BigEndian.PutUint64(timeout, uint64(TIMEOUT))
    player := make([]byte, 4)
    binary.BigEndian.PutUint32(player, uint32(player_id))
    nb_questions := make([]byte, 4)
    binary.BigEndian.PutUint32(nb_questions, uint32(len(questions)))
    var offsets []byte
    var pairs []byte
    temp_int := make([]byte, 4)
    //        (diff + cat) * nb_questions
    offset := 16 + 8 * len(questions)
    for i := 0; i < len(questions); i++ {
        // write current offset (start of data)
        binary.BigEndian.PutUint32(temp_int, uint32(offset))
        offsets = append(offsets, temp_int...)
        // write data and add length to offset (start of next data)
        pairs = append(pairs, []byte(questions[i].Difficulty)...)
        offset += len([]byte(questions[i].Difficulty))

        binary.BigEndian.PutUint32(temp_int, uint32(offset))
        offsets = append(offsets, temp_int...)
        pairs = append(pairs, []byte(questions[i].Category)...)
        offset += len([]byte(questions[i].Category))
    }
    var payload []byte
    payload = append(payload, timeout...)
    payload = append(payload, player...)
    payload = append(payload, nb_questions...)
    payload = append(payload, offsets...)
    payload = append(payload, pairs...)
    return CreateMessage(IN_GAME, CATEGORY_SELECTOR_ANNOUNCEMENT, payload)
}


func CreateQuestionMessage(q *Question) (Message, int) {
    /*
    +---------------------------------------------------------+
    P   Difficulty_Offset (4)    |    Category Offset (4)     P
    +---------------------------------------------------------+
    P    Question_Offset (4)     |       Num_Answers (4)      P
    +---------------------------------------------------------+
    P    Timeout_Millis (8)      |     Offset_Option_0 (4)    P
    +---------------------------------------------------------+
    P   Offset_Option_ ... (4)   |     Offset_Option_n (4)    P
    +---------------------------------------------------------+
    P     Difficulty     |    Category    |      Question     P
    +---------------------------------------------------------+
    P      Answer_0      |   Answer_ ...  |      Answer_n     P
    +---------------------------------------------------------+
    */
    var answers []string
    for i := 0; i < len(q.IncorrectAnswers); i++ {
        answers = append(answers, q.IncorrectAnswers[i])
    }
    answers = append(answers, q.CorrectAnswer)
    rand.Shuffle(len(answers), func(i, j int) {
        answers[i], answers[j] = answers[j], answers[i]
    })

    answer_id := -1

    for i := range answers {
        if answers[i] == q.CorrectAnswer {
            answer_id = i
            break
        }
    }

    timeout := make([]byte, 8)
    binary.BigEndian.PutUint64(timeout, uint64(TIMEOUT))
    nb_answers := make([]byte, 4)
    binary.BigEndian.PutUint32(nb_answers, uint32(len(answers)))
    temp_int := make([]byte, 4)
    var offsets []byte

    offset := 24 + 4 * len(answers)
    binary.BigEndian.PutUint32(temp_int, uint32(offset))
    offsets = append(offsets, temp_int...)
    offset += len([]byte(q.Difficulty))

    binary.BigEndian.PutUint32(temp_int, uint32(offset))
    offsets = append(offsets, temp_int...)
    offset += len([]byte(q.Category))

    binary.BigEndian.PutUint32(temp_int, uint32(offset))
    offsets = append(offsets, temp_int...)
    offset += len([]byte(q.Question))

    offsets = append(offsets, nb_answers...)
    offsets = append(offsets, timeout...)



    for i := 0; i < len(answers); i++ {
        // write current offset (start of data)
        binary.BigEndian.PutUint32(temp_int, uint32(offset))
        offsets = append(offsets, temp_int...)
        // write data and add length to offset (start of next data)
        offset += len([]byte(answers[i]))

    }
    var payload []byte
    payload = append(payload, offsets...)
    payload = append(payload, []byte(q.Difficulty)...)
    payload = append(payload, []byte(q.Category)...)
    payload = append(payload, []byte(q.Question)...)
    for i := range answers {
        payload = append(payload, []byte(answers[i])...)
    }
    return CreateMessage(IN_GAME, QUESTION, payload), answer_id
}

func CreateBuzzResultMessage(answerer int) Message {
    var payload []byte
    tmp_int := make([]byte, 4)
    tmp_int2 := make([]byte, 8)
    binary.BigEndian.PutUint32(tmp_int, uint32(answerer))
    payload = append(payload, tmp_int...)
    binary.BigEndian.PutUint64(tmp_int2, uint64(TIMEOUT))
    payload = append(payload, tmp_int2...)

    return CreateMessage(IN_GAME, BUZZ_RESULT, payload)
}

func CreateScrewResultMessage(answerer int, screwer int) Message {
    var payload []byte
    tmp_int := make([]byte, 4)
    tmp_int2 := make([]byte, 8)
    binary.BigEndian.PutUint32(tmp_int, uint32(screwer))
    payload = append(payload, tmp_int...)
    binary.BigEndian.PutUint32(tmp_int, uint32(answerer))
    payload = append(payload, tmp_int...)
    binary.BigEndian.PutUint64(tmp_int2, uint64(TIMEOUT))
    payload = append(payload, tmp_int2...)

    return CreateMessage(IN_GAME, SCREW_RESULT, payload)
}


func CreateAnswerResultMessage(answer int, correct int) Message {
    var payload []byte
    tmp_int := make([]byte, 4)
    binary.BigEndian.PutUint32(tmp_int, uint32(correct))
    payload = append(payload, tmp_int...)
    binary.BigEndian.PutUint32(tmp_int, uint32(answer))
    payload = append(payload, tmp_int...)

    return CreateMessage(IN_GAME, ANSWER_RESULT, payload)
}

func CreateTimeoutMessage(round int, state int, answerer int) Message {
    var payload []byte
    tmp_int := make([]byte, 4)
    binary.BigEndian.PutUint32(tmp_int, uint32(round))
    payload = append(payload, tmp_int...)
    binary.BigEndian.PutUint32(tmp_int, uint32(state))
    payload = append(payload, tmp_int...)
    binary.BigEndian.PutUint32(tmp_int, uint32(answerer))
    payload = append(payload, tmp_int...)

    return CreateMessage(IN_GAME, TIMEOUT_MESSAGE, payload)
}

func CreateScoreboardMessage(clients map[int]*Client, rounds_left int) Message {
    /*
     +---------------------------------------------+
     P    Rounds Left  (4)  |   Num Players   (4)  P*
     +---------------------------------------------+
     P    Player 1 ID  (4)  |  Player 1 Score (4)  P
     +---------------------------------------------+
     P                     ...                     P
     +---------------------------------------------+
     P    Player n ID  (4)  |  Player n Score (4)  P
     +---------------------------------------------+
     */
    var connected []*Client
    for id := range clients {
        if clients[id].Ready == READY {
            connected = append(connected, clients[id])
        }
    }


    var payload []byte
    tmp_int := make([]byte, 4)
    binary.BigEndian.PutUint32(tmp_int, uint32(rounds_left))
    payload = append(payload, tmp_int...)
    binary.BigEndian.PutUint32(tmp_int, uint32(len(connected)))
    payload = append(payload, tmp_int...)
    for id := range connected {
        binary.BigEndian.PutUint32(tmp_int, uint32(connected[id].Id))
        payload = append(payload, tmp_int...)
        binary.BigEndian.PutUint32(tmp_int, uint32(connected[id].Score))
        payload = append(payload, tmp_int...)
    }
    return CreateMessage(IN_GAME, SCOREBOARD, payload)
}


func (m *Message) tobin() []byte {
    var bytes []byte
    bytes = append(bytes, byte(m.Version))
    bytes = append(bytes, byte(m.Group))
    bytes = append(bytes, byte(m.Type))
    length := make([]byte, 4)
    binary.BigEndian.PutUint32(length, uint32(m.Length))
    bytes = append(bytes, length...)
    bytes = append(bytes, m.Payload...)
    return bytes
}


func (m *Message) ValidLength() bool {
    if m.Type == TIMEOUT_MESSAGE {
        return true
    }
    switch m.Group {
        case PRE_GAME:
            switch m.Type {
                case SIGN_ON:
                    return true
                case PLAYER_READY:
                    return m.Length == 0
                default:
                    return false
            }

        case IN_GAME:
            switch m.Type {
                case CATEGORY_SELECTION:
                    return m.Length == 4
                case ANSWER:
                    return m.Length == 4
                case BUZZ:
                    return m.Length == 0
                case SCREW:
                    return m.Length == 4
                default:
                    return false
            }

        case GENERAL:
            if m.Type == GENERAL_TEXT {
                return true
            }
            return false
        default:
            return false
    }

    return false
}

func CreateMessage(group int, type_ int, payload []byte) Message {
    var msg Message
    msg.Version = VERSION
    msg.Group = group
    msg.Type = type_
    msg.Length = len(payload)
    msg.Payload = payload
    return msg
}

func (c *Client) ReadMessage() (Message, error) {
    var msg Message
    msg.Sender = c
    header := make([]byte, 7)
    n, err := c.Conn.Read(header)
    if err != nil {
        return Message{}, err
    }
    if n != 7 {
        q.Q(n, header)
        return Message{}, HeaderReadError
    }
    msg.Version = int(header[0])
    msg.Group = int(header[1])
    msg.Type = int(header[2])
    msg.Length = int(binary.BigEndian.Uint32(header[3:]))
    if msg.Length != 0 {
        msg.Payload = make([]byte, msg.Length)
        n, err = c.Conn.Read(msg.Payload)
        if n != msg.Length || err != nil {
            q.Q(msg.Length, n, err)
            return Message{}, PayloadReadError
        }
    }
    return msg, nil
}

func (c *Client) SendMessage(msg Message) error {
    q.Q("server -->> client")
    q.Q(msg)
    n, err := c.Conn.Write(msg.tobin())
    if n != msg.Length + 7 {
        fmt.Println("[Message] Send message, incorrect write: ", n, " != ", msg.Length + 7)
        return WriteError
    }
    if err != nil {
        fmt.Println("[Message] Send message, error:", err)
        return err
    }
    return nil
}
