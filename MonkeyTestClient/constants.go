package main

var groups = []string{"PRE_GAME", "IN_GAME", "POST_GAME", "GENERAL"}
var pre_game = []string{"SIGN_ON", "SIGN_ON_RESPONSE", "PLAYER_READY"}
var in_game = []string{"CATEGORY_SELECTOR_ANNOUNCEMENT", "CATEGORY_SELECTION", "QUESTION", "ANSWER", "BUZZ", "BUZZ_RESULT",
                     "SCOREBOARD", "ANSWER_RESULT", "SCREW", "SCREW_RESULT"}
var post_game = []string{"GAME_END"}
var general = []string{"GENERAL_TEXT", "PLAYER_LIST"}
var s_states = []string{"S_PRE_GAME", "S_CATEGORY", "S_QUESTION", "S_BUZZ", "S_SCREW"}


const (
// message_groups
    PRE_GAME    = 0
    IN_GAME     = 1
    POST_GAME   = 2
    GENERAL     = 3

// pre_game_message_types
    SIGN_ON             = 0
    SIGN_ON_RESPONSE    = 1
    PLAYER_READY        = 2

// in_game_message_types
    CATEGORY_SELECTOR_ANNOUNCEMENT      = 0
    CATEGORY_SELECTION                  = 1
    QUESTION                            = 2
    ANSWER                              = 3
    BUZZ                                = 4
    BUZZ_RESULT                         = 5
    SCOREBOARD                          = 6
    ANSWER_RESULT                       = 7
    SCREW                               = 8
    SCREW_RESULT                        = 9
    TIMEOUT_MESSAGE                     = 999 // custom extension

// post_game_message_types
    GAME_END = 0
// general_message_types
    GENERAL_TEXT = 0
    PLAYER_LIST = 1

// player_list constants
    NOT_READY = 0
    READY = 1
    DISCONNECTED = 2
// Protocol version

    VERSION = 1

// server port
    PORT = "8081"
// server states
    S_PRE_GAME = 0
    S_CATEGORY = 1
    S_QUESTION = 2
    S_BUZZ = 3
    S_SCREW = 4

// game configuration
    MIN_PLAYERS = 2
    MAX_PLAYERS = 16
    ROUNDS      = 5
    TIMEOUT     = 15000 // 15 seconds
)
