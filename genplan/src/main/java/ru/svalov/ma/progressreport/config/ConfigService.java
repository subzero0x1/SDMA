package ru.svalov.ma.progressreport.config;

import java.util.List;

public interface ConfigService {
    List<Board> getBoards(String xml);
}