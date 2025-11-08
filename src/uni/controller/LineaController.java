package uni.controller;

import java.util.List;
import uni.dao.LineaDao;
import uni.entity.LineaTo;

public class LineaController {

    LineaDao dao = new LineaDao();

    public LineaController() {
    }

    public List<LineaTo> LineaListar() throws Exception {
        return dao.readAll();
    }
}
