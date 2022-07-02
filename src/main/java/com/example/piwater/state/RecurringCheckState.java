package com.example.piwater.state;

import lombok.Data;
import org.springframework.stereotype.*;

@Data
@Component
public class RecurringCheckState {

    private long latestCheckTime;
}
