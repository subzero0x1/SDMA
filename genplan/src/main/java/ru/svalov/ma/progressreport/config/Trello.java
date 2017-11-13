package ru.svalov.ma.progressreport.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "trello")
public class Trello {

    private List<Board> boards = new ArrayList<>();

    @XmlElement(name = "board")
    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }
}
