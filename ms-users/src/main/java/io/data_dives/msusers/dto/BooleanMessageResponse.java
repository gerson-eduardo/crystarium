package io.data_dives.msusers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BooleanMessageResponse {
    private String message;
    private boolean result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanMessageResponse that = (BooleanMessageResponse) o;
        return result == that.result && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, result);
    }
}
