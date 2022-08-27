package com.betpawa.bet.rest.request;

public record BetActionRequest(Long accountId, Long betId, String action) {
}