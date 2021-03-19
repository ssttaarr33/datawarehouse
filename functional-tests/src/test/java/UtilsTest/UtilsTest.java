package UtilsTest;

import com.adrian.interview.utils.misc.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    @DisplayName("Check textToDate")
    void testTextToDate(){
        assertEquals(LocalDate.of(2019,11,12), Utils.textToDate("11/12/19"));
    }
}
