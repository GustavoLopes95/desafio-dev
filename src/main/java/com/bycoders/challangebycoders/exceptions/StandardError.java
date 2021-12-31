package com.bycoders.challangebycoders.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError<T> {
    private String message;
    private List<T> errors;

    public StandardError(String message) {
        this.message = message;
    }
}
