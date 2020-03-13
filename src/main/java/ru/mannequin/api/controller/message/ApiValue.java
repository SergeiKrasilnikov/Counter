package ru.mannequin.api.controller.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiValue {
    private boolean success;
    private int value;
}
