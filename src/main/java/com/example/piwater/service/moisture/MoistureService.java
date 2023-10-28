package com.example.piwater.service.moisture;


import com.example.piwater.model.Moisture;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface MoistureService {

    List<Moisture> getCurrentMoistureValues() throws IOException;

    List<Moisture> getHistoricalMoistureValues(LocalDateTime since) throws IOException;

    void saveCurrentMoistureValue(Moisture moisture) throws IOException;
}
