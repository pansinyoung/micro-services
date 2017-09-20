package com.gcit.lms.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.hbm.*;

@CrossOrigin
@RestController
public class AllService {
	
	@PersistenceContext
	private EntityManager em;
	
//	@Autowired
//	AuthorDAO adao;
//	
//	@Autowired
//	BookCopiesDAO bcdao;
//	
//	@Autowired
//	BookDAO bdao;
//	
//	@Autowired
//	BranchDAO brdao;
//	
//	@Autowired
//	BorrowerDAO bodao;
//	
//	@Autowired
//	LoanDAO ldao;
//	
//	@Autowired
//	GenreDAO gdao;
//	
//	@Autowired
//	PublisherDAO pdao;
	
	@RequestMapping(value = "/book", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void addBook(@RequestBody TblBook book){
		
		try {
			em.persist(book);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/book", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void updateBook(@RequestBody TblBook book){
		try {
			em.merge(book);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/author", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void addAuthor(@RequestBody TblAuthor author){
		try {
			em.persist(author);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/author", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void updateAuthor(@RequestBody TblAuthor author){
		try {
			em.merge(author);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/publisher", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void addPublisher(@RequestBody TblPublisher publisher){
		try {
			em.persist(publisher);
			for(TblBook b: publisher.getBooks()) {
				b.setPublisher(publisher);
				em.merge(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/publisher", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void updatePublisher(@RequestBody TblPublisher publisher){
		try {
			em.merge(publisher);
			for(TblBook b: publisher.getBooks()) {
				b.setPublisher(publisher);
				em.merge(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/branch", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void addBranch(@RequestBody TblBranch branch){
		try {
			em.persist(branch);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/branch", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void updateBranch(@RequestBody TblBranch branch){
		try {
			em.createQuery("update TblBranch set branchName = :branchName, branchAddr = :branchAddr where branchId = :branchId")
				.setParameter("branchName", branch.getBranchName())
				.setParameter("branchAddr", branch.getBranchAddr())
				.setParameter("branchId", branch.getBranchId())
				.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/borrower", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void addBorrower(@RequestBody TblBorrower borrower) {
		try {
			em.persist(borrower);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/borrower", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void updateBorrower(@RequestBody TblBorrower borrower) {
		try {
			em.merge(borrower);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/genre", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void addGenre(@RequestBody TblGenre genre) {
		try {
			em.persist(genre);
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/genre", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void updateGenre(@RequestBody TblGenre genre) {
		try {
			em.merge(genre);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/loan", method = RequestMethod.POST, consumes="application/json")
	@Transactional
	public void addLoan(@RequestBody TblLoan loan){
		try {
//			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime due = now.plusDays(7);
	        loan.setDateOut(now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth());
	        loan.setDueDate(due.getYear() + "-" + due.getMonthValue() + "-" + due.getDayOfMonth());
	        TblLoanId pk = new TblLoanId();
	        pk.setBookId(loan.getBook().getBookId());
	        pk.setBranchId(loan.getBranch().getBranchId());
	        pk.setCardNo(loan.getBorrower().getCardNo());
	        pk.setDateOut(loan.getDateOut());
	        loan.setId(pk);
	        loan.setDateIn(null);
	        em.persist(loan);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/loan/dueDate", method = RequestMethod.PUT, consumes="application/json")
	@Transactional
	public void updateLoanDueDate(@RequestBody TblLoan loan){
		try {
			em.merge(loan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/book/{bookId}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteBook(@PathVariable String bookId) {
		try {
			em.remove(em.find(TblBook.class, Integer.parseInt(bookId)));
		} catch ( Exception e) {
			e.printStackTrace();
		}  
	}
	
	@RequestMapping(value = "/author/{authorId}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteAuthor(@PathVariable String authorId){
		try {
			em.remove(em.find(TblAuthor.class, Integer.parseInt(authorId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/publisher/{publisherId}", method = RequestMethod.DELETE)
	@Transactional
	public void deletePublisher(@PathVariable String publisherId){
		try {
			em.remove(em.find(TblPublisher.class, Integer.parseInt(publisherId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/branch/{branchId}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteBranch(@PathVariable String branchId){
		try {
			em.remove(em.find(TblBranch.class, Integer.parseInt(branchId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/borrower/{cardNo}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteBorrower(@PathVariable String cardNo){
		try {
			em.remove(em.find(TblBorrower.class, Integer.parseInt(cardNo)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/genre/{genre_id}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteGenre(@PathVariable String genre_id){
		try {
			em.remove(em.find(TblGenre.class, Integer.parseInt(genre_id)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@RequestMapping(value = "/book", params = {"searchString"}, method = RequestMethod.GET, produces="application/json")
	public List<TblBook> readAllBook(@RequestParam(value = "searchString") String searchString){
		try {
			@SuppressWarnings("unchecked")
			List<TblBook> result = em.createQuery("from TblBook where title like :searchString").setParameter("searchString", "%" + searchString + "%").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/book", method = RequestMethod.GET, produces="application/json")
	public List<TblBook> readAllBooks(){
		try {
			@SuppressWarnings("unchecked")
			List<TblBook> result = em.createQuery("from TblBook").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@RequestMapping(value = "/author", params = {"searchString"}, method = RequestMethod.GET, produces="application/json")
	public List<TblAuthor> readAllAuthor(@RequestParam(value = "searchString") String searchString){
		try {
			@SuppressWarnings("unchecked")
			List<TblAuthor> result = em.createQuery("from TblAuthor where authorName like :searchString").setParameter("searchString", "%" + searchString + "%").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/author", method = RequestMethod.GET, produces="application/json")
	public List<TblAuthor> readAllAuthors(){
		try {
			@SuppressWarnings("unchecked")
			List<TblAuthor> result = em.createQuery("from TblAuthor").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/loan", params = {"searchString"}, method = RequestMethod.GET, produces="application/json")
	public List<TblLoan> readAllLoan(@RequestParam(value = "searchString") String searchString){
		try {
			@SuppressWarnings("unchecked")
			List<TblLoan> result = em.createQuery("from TblLoan where authorName like :searchString").setParameter("searchString", "%" + searchString + "%").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/loan", method = RequestMethod.GET, produces="application/json")
	public List<TblLoan> readAllLoans(){
		try {
			@SuppressWarnings("unchecked")
			List<TblLoan> result = em.createQuery("from TblLoan").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/publisher", params = {"searchString"}, method = RequestMethod.GET, produces="application/json")
	public List<TblPublisher> readAllPublisher(@RequestParam(value = "searchString") String searchString){
		try {
			@SuppressWarnings("unchecked")
			List<TblPublisher> result = em.createQuery("from TblPublisher where publisherName like :searchString").setParameter("searchString", "%" + searchString + "%").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/publisher", method = RequestMethod.GET, produces="application/json")
	public List<TblPublisher> readAllPublishers(){
		try {
			@SuppressWarnings("unchecked")
			List<TblPublisher> result = em.createQuery("from TblPublisher").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/branch", params = {"searchString"}, method = RequestMethod.GET, produces="application/json")
	public List<TblBranch> readAllBranch(@RequestParam(value = "searchString") String searchString){
		try {
			@SuppressWarnings("unchecked")
			List<TblBranch> result = em.createQuery("from TblBranch where branchName like :searchString").setParameter("searchString", "%" + searchString + "%").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/branch", method = RequestMethod.GET, produces="application/json")
	public List<TblBranch> readAllBranches(){
		try {
			@SuppressWarnings("unchecked")
			List<TblBranch> result = em.createQuery("from TblBranch").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/borrower", params = {"searchString"}, method = RequestMethod.GET, produces="application/json")
	public List<TblBorrower> readAllBorrower(@RequestParam(value = "searchString") String searchString){
		try {
			@SuppressWarnings("unchecked")
			List<TblBorrower> result = em.createQuery("from TblBorrower where name like :searchString").setParameter("searchString", "%" + searchString + "%").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/borrower", method = RequestMethod.GET, produces="application/json")
	public List<TblBorrower> readAllBorrowers(){
		try {
			@SuppressWarnings("unchecked")
			List<TblBorrower> result = em.createQuery("from TblBorrower").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/genre", params = {"searchString"}, method = RequestMethod.GET, produces="application/json")
	public List<TblGenre> readAllGenre(@RequestParam(value = "searchString") String searchString){
		try {
			@SuppressWarnings("unchecked")
			List<TblGenre> result = em.createQuery("from TblGenre where genre_name like :searchString").setParameter("searchString", "%" + searchString + "%").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/genre", method = RequestMethod.GET, produces="application/json")
	public List<TblGenre> readAllGenres(){
		try {
			@SuppressWarnings("unchecked")
			List<TblGenre> result = em.createQuery("from TblGenre").getResultList();
			//TODO add related entities
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//
//	@RequestMapping(value = "/authors", params = {"bookId"}, method = RequestMethod.GET, produces="application/json")
//	public List<Author> viewBookAuthors(@RequestParam(value = "bookId") String bookId){
//		try {
//			List<Author> result = adao.searchByBookId(Integer.parseInt(bookId));
//			for(Author a: result) {
//				a.setBooks(bdao.searchByAuthorId(a.getAuthorId()));
//			}
//			return result;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@RequestMapping(value = "/book", params = {"authorId"}, method = RequestMethod.GET, produces="application/json")
//	public List<Book> viewAuthorBooks(@RequestParam(value = "authorId") String authorId){
//		try {
//			List<Book> result = bdao.searchByAuthorId(Integer.parseInt(authorId));
//			for(Book b: result) {
//				b.setAuthors(adao.searchByBookId(b.getBookId()));
//				b.setCopies(bcdao.readCopiesByBookId(b.getBookId()));
//				b.setGenres(gdao.searchByBookId(b.getBookId()));
//				b.setLoans(ldao.getResultByBookId(b.getBookId()));
//				b.setPublisher(pdao.getById(b.getPublisher().getPublisherId()));
//			}
//			return result;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@RequestMapping(value = "/publisher", params = {"bookId"}, method = RequestMethod.GET, produces="application/json")
//	public Publisher viewBookPublisher(@RequestParam(value = "bookId") String bookId){
//		try {
//			Publisher result = pdao.getById(Integer.parseInt(bookId));
//			result.setBooks(bdao.searchByGenreId(result.getPublisherId()));
//			return result;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@RequestMapping(value = "/book", params = {"publisherId"}, method = RequestMethod.GET, produces="application/json")
//	public List<Book> viewPublisherBooks(@RequestParam(value = "publisherId") String pubId){
//		try {
//			List<Book> result = bdao.searchByPublisherId(Integer.parseInt(pubId));	
//			for(Book b: result) {
//				b.setAuthors(adao.searchByBookId(b.getBookId()));
//				b.setCopies(bcdao.readCopiesByBookId(b.getBookId()));
//				b.setGenres(gdao.searchByBookId(b.getBookId()));
//				b.setLoans(ldao.getResultByBookId(b.getBookId()));
//				b.setPublisher(pdao.getById(b.getPublisher().getPublisherId()));
//			}
//			return result;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@RequestMapping(value = "/genres", params = {"bookId"}, method = RequestMethod.GET, produces="application/json")
//	public List<Genre> viewBookGenres(@RequestParam(value = "bookId") String bookId){
//		try {
//			List<Genre> result = gdao.searchByBookId(Integer.parseInt(bookId));
//			for(Genre g: result) {
//				g.setBooks(bdao.searchByGenreId(g.getGenre_id()));
//			}
//			return result;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@RequestMapping(value = "/book", params = {"genreId"}, method = RequestMethod.GET, produces="application/json")
//	public List<Book> viewGenreBooks(@RequestParam(value = "genreId") String genreId){
//		try {
//			List<Book> result = bdao.searchByGenreId(Integer.parseInt(genreId));
//			for(Book b: result) {
//				b.setAuthors(adao.searchByBookId(b.getBookId()));
//				b.setCopies(bcdao.readCopiesByBookId(b.getBookId()));
//				b.setGenres(gdao.searchByBookId(b.getBookId()));
//				b.setLoans(ldao.getResultByBookId(b.getBookId()));
//				b.setPublisher(pdao.getById(b.getPublisher().getPublisherId()));
//			}
//			return result;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
	@RequestMapping(value = "/publisher", params = {"publisherId"}, method = RequestMethod.GET, produces="application/json")
	public TblPublisher getPublisherById(@RequestParam(value = "publisherId") String publisherId) {
		try {
			TblPublisher result = em.find(TblPublisher.class, Integer.parseInt(publisherId));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/book", params = {"bookId"}, method = RequestMethod.GET, produces="application/json")
	public TblBook selectBookById(@RequestParam(value = "bookId") String bookId) {
		try {
			TblBook result = em.find(TblBook.class, Integer.parseInt(bookId));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/author", params = {"authorId"}, method = RequestMethod.GET, produces="application/json")
	public TblAuthor selectAuthorById(@RequestParam(value = "authorId") String authorId) {
		try {
			TblAuthor result = em.find(TblAuthor.class, Integer.parseInt(authorId));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/branch", params = {"branchId"}, method = RequestMethod.GET, produces="application/json")
	public TblBranch selectBranchById(@RequestParam(value = "branchId") String branchId) {
		try {
			TblBranch result = em.find(TblBranch.class, Integer.parseInt(branchId));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/borrower", params = {"cardNo"}, method = RequestMethod.GET, produces="application/json")
	public TblBorrower selectBorrowerById(@RequestParam(value = "cardNo") String cardNo) {
		try {
			TblBorrower result = em.find(TblBorrower.class, Integer.parseInt(cardNo));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/genre", params = {"genre_id"}, method = RequestMethod.GET, produces="application/json")
	public TblGenre selectGenreById(@RequestParam(value = "genre_id") String genreId) {
		try {
			TblGenre result = em.find(TblGenre.class, Integer.parseInt(genreId));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/copies", params = {"bookId", "branchId"}, method = RequestMethod.GET, produces="application/json")
	public TblBookCopies selectCopiesByBothId(@RequestParam(value = "bookId") String bookId, @RequestParam(value = "branchId") String branchId) {
		try {
			TblBookCopiesId pk = new TblBookCopiesId();
			pk.setBookId(Integer.parseInt(bookId));
			pk.setBranchId(Integer.parseInt(branchId));
			TblBookCopies result = em.find(TblBookCopies.class, pk);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/copies", params = {"branchId"}, method = RequestMethod.GET, produces="application/json")
	public List<TblBookCopies> selectCopiesByBranchId(@RequestParam(value = "branchId") String branchId) {
		try {
			@SuppressWarnings("unchecked")
			List<TblBookCopies> result = em.createQuery("from TblBookCopies where id.branchId = :branchId").setParameter("branchId", Integer.parseInt(branchId)).getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/copies", method = RequestMethod.GET, produces="application/json")
	public List<TblBookCopies> selectAllCopies() {
		try {
			@SuppressWarnings("unchecked")
			List<TblBookCopies> result = em.createQuery("from TblBookCopies").getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/loan/dateIn", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void bookReturn(@RequestBody TblLoan loan) {
		try {
//			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
	        loan.setDateIn(now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth());
	        em.merge(loan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/copies/noOfCopies", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void updateCopies(@RequestBody TblBookCopies bc) {
		try {
			em.merge(bc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/branch/noOfCopies", method = RequestMethod.PUT, consumes="application/json", produces="application/json")
	@Transactional
	public void addCopiesToBranch(@RequestBody TblBookCopies bc) {
		try {
			TblBookCopiesId pk = new TblBookCopiesId();
			pk.setBookId(bc.getBook().getBookId());
			pk.setBranchId(bc.getBranch().getBranchId());
			TblBookCopies temp = em.find(TblBookCopies.class, pk);
			bc.setId(pk);
			if(temp != null) {
				temp.setNoOfCopies(bc.getNoOfCopies()+temp.getNoOfCopies());
				em.merge(temp);
			}else {
				em.persist(bc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
