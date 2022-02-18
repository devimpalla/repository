package service;

import java.util.List;

import domain.CommentVO;
import domain.ProductVO;

public interface CommentService {
	public int register(CommentVO cvo);
	public List<CommentVO> getList(long pno);
	public int modify(CommentVO cvo);
	public int remove(long cno);
}
