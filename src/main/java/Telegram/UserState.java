package Telegram;

public enum UserState {
    WAITING_FOR_TRUE_POSITIVE,
    WAITING_FOR_FALSE_POSITIVE,
    WAITING_FOR_FALSE_NEGATIVE,
    WAITING_FOR_TRUE_NEGATIVE,
    WAITING_FOR_INDEX,
    WAITING_FOR_METRICS,
    WAITING_FOR_METRIC_INPUT
}
