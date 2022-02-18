package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.ProductVO;
import net.coobird.thumbnailator.Thumbnails;
import service.ProductService;
import service.ProductServiceImple;


@WebServlet("/prodCtrl/*")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	private final ProductService psv;
	private RequestDispatcher rdp;
	private int isUp;
	private final String UTF8 = "utf-8";
	
    
    public ProductController() {
        psv = new ProductServiceImple();
    }

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding(UTF8);
    	res.setCharacterEncoding(UTF8);
    	res.setContentType("text/html; charset=utf-8");
    	
    	String uri = req.getRequestURI();
    	String path = uri.substring(uri.lastIndexOf("/")+1);
    	
    	 
    	switch (path) {
		case "create":
			rdp = req.getRequestDispatcher("/product/register.jsp");
			rdp.forward(req, res);
			break;
			
		case "insert":
			try {
				String savePath = getServletContext().getRealPath("/_fileUpload");
		    	 File fileDir = new File(savePath);
		    	 
		    	 DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
					fileItemFactory.setRepository(fileDir);
					fileItemFactory.setSizeThreshold(1*1024*1024); // 저장을 위한 임시 메모리 저장용량 Byte단위 파일첨부할때 이 용량넘으면 에러
					ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		    	
				
				ProductVO pvo = new ProductVO();
				List<FileItem> itemList = fileUpload.parseRequest(req);
				for(FileItem item: itemList) {
					switch (item.getFieldName()) {
					case "pname":
						pvo.setPname(item.getString(UTF8));
						break;
					case "price":
						pvo.setPrice(Integer.parseInt(item.getString(UTF8)));
						break;
					case "madeBy":
						pvo.setMadeBy(item.getString(UTF8));
						break;
					case "writer":
						pvo.setWriter(item.getString(UTF8));
						break;
					case "category":
						pvo.setCategory(item.getString(UTF8));
						break;
					case "description":
						pvo.setDescription(item.getString(UTF8));
						break;
					case "imageFile":
						if(item.getSize() > 0) {
							String fileName = item.getName() //  파일 경로가 포함된 전체 이름
									.substring(item.getName()
											.lastIndexOf(File.separator) + 1); // 파일경로 구분자인 \가 마지막으로 있는 위치값
							// 실제 저장될 path로 객체화
							fileName = System.currentTimeMillis()+"-" + fileName;
							File uploadFilePath =
									new File(fileDir + File.separator + fileName);
							try {
								item.write(uploadFilePath);
								pvo.setImageFile(fileName);
								
								Thumbnails.of(uploadFilePath)
						        .size(75, 75)
						        .toFile(new File(fileDir + File.separator +"th_"+ fileName));
								log.info(">>> {}", UUID.randomUUID().toString());
							} catch (Exception e) {
								log.info(">>> File Write To Disk > Fail");
								e.printStackTrace();
							}
						}
						break;

					default:
						break;
					}
				}
				log.info(">>> pvo > {}", pvo);
				psv.register(pvo);
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			rdp = req.getRequestDispatcher("/prodCtrl/list");
			rdp.forward(req, res);
			break;
			
		case "list":
			req.setAttribute("list",psv.getList());
			rdp =  req.getRequestDispatcher("/product/list.jsp");
			rdp.forward(req, res);
			break;
			
		case "detail":
			req.setAttribute("pvo", psv.getDetail(Long.parseLong(req.getParameter("pno"))));
			log.info(req.getParameter("pno"));
			rdp = req.getRequestDispatcher("/product/detail.jsp");
			rdp.forward(req, res);
			break;
			
		case "modify":
			req.setAttribute("pvo", psv.getDetail(Long.parseLong(req.getParameter("pno"))));
			log.info(req.getParameter("pno"));
			rdp = req.getRequestDispatcher("/product/modify.jsp");
			rdp.forward(req, res);
			break;
			
		case "update":
			ProductVO pvo = new ProductVO();
			String[] imageFileNames =  new String[2];
			try {
			String savePath = getServletContext().getRealPath("/_fileUpload");
	    	File fileDir = new File(savePath);
	    	 
	    	 DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			fileItemFactory.setRepository(fileDir);
			fileItemFactory.setSizeThreshold(1*1024*1024); // 저장을 위한 임시 메모리 저장용량 Byte단위 파일첨부할때 이 용량넘으면 에러
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(req);
				for(FileItem item: itemList) {
					switch (item.getFieldName()) {
					case "pname":
						pvo.setPname(item.getString(UTF8));
						break;
					case "price":
						pvo.setPrice(Integer.parseInt(item.getString(UTF8)));
						break;
					case "madeBy":
						pvo.setMadeBy(item.getString(UTF8));
						break;
					case "category":
						pvo.setCategory(item.getString(UTF8));
						break;
					case "description":
						pvo.setDescription(item.getString(UTF8));
						break;
					case "pno":
						pvo.setPno(Long.parseLong(item.getString(UTF8)));
						break;
					case "imageFile":
						pvo.setImageFile(item.getString(UTF8));
						imageFileNames[0] = pvo.getImageFile();
						break;
					case "new_imageFile":
						if(item.getSize() > 0) {
							String fileName = item.getName() //  파일 경로가 포함된 전체 이름
									.substring(item.getName()
											.lastIndexOf(File.separator) + 1); // 파일경로 구분자인 \가 마지막으로 있는 위치값
							// 실제 저장될 path로 객체화
							fileName = System.currentTimeMillis()+"-" + fileName;
							File uploadFilePath =
									new File(fileDir + File.separator + fileName);
							try {
								item.write(uploadFilePath);
								pvo.setImageFile(fileName);
								imageFileNames[1] = pvo.getImageFile();
								
								Thumbnails.of(uploadFilePath)
						        .size(75, 75)
						        .toFile(new File(fileDir + File.separator +"th_"+ fileName));
								log.info(">>> {}", UUID.randomUUID().toString());
							} catch (Exception e) {
								log.info(">>> File Write To Disk > Fail");
								e.printStackTrace();
							}
						}
						break;

					default:
						break;
					} // swtich 끝
	
				} // forEach 끝
				if (imageFileNames[0].length() > 0 && imageFileNames[1].length() > 0) {
					try {
						String savePath2 = getServletContext().getRealPath("/_fileUpload");
				    	File fileDir2 = new File(savePath);
						
						File removeFile = new File(fileDir + File.separator + imageFileNames[0]);
						File removeFileThumb =
								new File(fileDir + File.separator +"th_" + imageFileNames[0]);
						boolean rm = true;
						if(removeFile.exists() || removeFileThumb.exists()) {
							rm = removeFile.delete();
							if (rm) {
								rm = removeFileThumb.delete();
							}
						}
						log.info(">>> REMOVE File > {}", rm? "Success":"Fail");
						}catch(Exception e) {
							
						}
				}
					
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			isUp = psv.modify(pvo);
					
			log.info(">>> MODIFY > {}", (isUp > 0 ? "Success":"Fail"));
			rdp = req.getRequestDispatcher("/prodCtrl/detail?pno="+pvo.getPno());
			rdp.forward(req, res);
			break;
			
		case "remove":
			try {
			String savePath = getServletContext().getRealPath("/_fileUpload");
	    	File fileDir = new File(savePath);
			
			log.info(">>> pno > {}", req.getParameter("pno"));
			log.info(">>> imageFile > {}", req.getParameter("imageFile"));
			
			File removeFile = new File(fileDir + File.separator + req.getParameter("imageFile"));
			File removeFileThumb =
					new File(fileDir + File.separator +"th_" + req.getParameter("imageFile"));
			boolean rm = true;
			if(removeFile.exists() || removeFileThumb.exists()) {
				rm = removeFile.delete();
				if (rm) {
					rm = removeFileThumb.delete();
				}
			}
			if (rm) {
				isUp = psv.remove(Long.parseLong(req.getParameter("pno")));
			}
			}catch(Exception e) {
				
			}
			
			
			log.info(">>> REMOVE > {}", (isUp > 0 ? "Success":"Fail"));
			
			req.setAttribute("msg_remove", isUp);
			rdp = req.getRequestDispatcher("/prodCtrl/list");
			rdp.forward(req, res);
			break;

		default:
			break;
		}
	}

}
