package ku_rum.backend.testRedis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("boards")
public class BoardController {

  private BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping()
  public List<Board> getBoards(
          @RequestParam(defaultValue = "1", value="page") int page, @RequestParam(defaultValue = "10",value="size") int size
  ) {
    return boardService.getBoards(page, size);
  }

}