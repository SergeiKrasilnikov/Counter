package ru.mannequin.api.controller.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiList {
    private boolean success;
    private List<String> names;
}
