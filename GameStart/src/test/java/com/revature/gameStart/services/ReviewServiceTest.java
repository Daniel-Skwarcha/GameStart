package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ReviewServiceTest {



    @Spy
    private ReviewService spyreviewService;

    @Spy
    private ReviewRepository spyreviewRepo;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    List<Review> reviews = new ArrayList<>();
    List<Review> reviewsUG = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<Game> games = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        //spyreviewService = new ReviewService(reviewRepository);

        genres.add(new Genre(1,"fps"));
        users.add(new User(1, "ree", "ew", "APww", "Passwww", "aefp@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        games.add(new Game(1,"GTA", genres, "GTA game", 2));
        reviews.add(new Review("work", 5, games.get(0), users.get(0)));
        reviewsUG.add(new Review("work", 5, games.get(0), users.get(0)));

//        Review review = new Review("work", 5, games.get(0), users.get(0));

    }

    @After
    public void tearDown() {
        reviews.clear();
        users.clear();
        games.clear();
        genres.clear();
    }

    //-----------------------------Test------------------------------


    @Ignore@Test
    public void registerReviewTest() {

        when(reviewRepository.save(reviews.get(0))).thenReturn(reviews.get(0));

        reviewService.registerReview(reviews.get(0));

        verify(reviewRepository, times(1)).save(reviews.get(0));

    }

    @Test
    public void registerReviewIfExistTest() {
        Review existReview = new Review("work", 5, games.get(0), users.get(0));
        List<Review> newReviews = new ArrayList<>();
        newReviews.add(existReview);
        reviewService.registerReview(newReviews.get(0));
        assertEquals(reviews.get(0),newReviews.get(0));
       // verify(reviewRepository, times(1)).save(reviews.get(1));
    }



    @Ignore@Test
    public void findAllTest() {

        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> testReviews = reviewService.findAllReview();

        assertEquals(1, testReviews.size());
       verify(reviewRepository, times(1)).findAll();
    }


    @Ignore@Test
    public void findReviewByUserIdandGameId(){
        when(reviewRepository.findReviewByUserAndGame(users.get(0).getId(), games.get(0).getId())).thenReturn(Optional.ofNullable(reviews.get(0)));

        Review testReview = reviewService.getReviewByUserAndGameId(users.get(0).getId(), games.get(0).getId());

        assertEquals(testReview,reviews.get(0));

    }

    @Ignore
    @Test
    public void findReviewByGameId(){

        when(reviewRepository.findReviewByGameId(games.get(0).getId())).thenReturn(reviewsUG);

        List<Review> testReview = reviewService.getReviewsByGameId(games.get(0).getId());

        assertEquals(testReview,reviewsUG);

    }


    @Ignore
    @Test
    public void findReviewByUserId() {
        when(reviewRepository.findReviewByUserId(users.get(0).getId())).thenReturn(reviewsUG);

        List<Review> testReview = reviewService.getReviewsByUserId(users.get(0).getId());

        assertEquals(testReview, reviewsUG);

    }


    @Ignore
    @Test
    public void updateReviewTest(){
        Review newReview = new Review("hiugyuvovy",0,games.get(0),users.get(0));
       // when(reviewRepository.updateReview(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getDescription(), newReview.getScore())).thenReturn(reviews.get(0));
       Optional<Review> existReview = Optional.of(newReview);
       doReturn(existReview).when(spyreviewRepo).findReviewByUserAndGame(games.get(0).getId(),users.get(0).getId());
   //     when(reviewRepository.save(newReview)).thenReturn(newReview);
//        reviewService.updateReview(newReview);
//        reviews.set(0,newReview);
       when(reviewRepository.save(newReview)).thenReturn(newReview);

        reviewService.updateReview(newReview);
        assertNotEquals(reviews.get(0),newReview);
       // verify(reviewRepository, times(1)).save(newReview);
//        Mockito.doThrow(new Exception()).when(reviewRepository.save(newReview)).s;


    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateReviewTestNotPresent(){
        Review newReview = new Review("hiugyuvovy",0,games.get(0),users.get(0));
        //when(reviewRepository.updateReview(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getDescription(), newReview.getScore())).thenReturn(reviews.get(0));
        when(reviewRepository.save(newReview)).thenReturn(newReview);
       reviewService.updateReview(newReview);

       //assertEquals(reviews.get(0),newReview);
        verify(reviewRepository, times(1)).save(newReview);
    }


    @Ignore
    @Test
    public void updateReviewDescriptionTest(){
//        when(reviewRepository.updateDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION")).thenReturn(reviews.get(0));
//
//        reviewService.updateReviewDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION");
//
//        verify(reviewRepository, times(1)).updateDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION");
        Review newReview = new Review("hiugyuvovy",0,games.get(0),users.get(0));
        //Optional<Review> existReview = Optional.of(newReview);
        doReturn(newReview).when(spyreviewRepo).updateDescription(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getDescription());
        when(reviewRepository.updateDescription(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getDescription())).thenReturn(newReview);

        reviewService.updateReviewDescription(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getDescription());
        assertNotEquals(reviews.get(0),newReview);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateReviewDescriptionTestNotPresent(){
        when(reviewRepository.updateDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION")).thenReturn(reviews.get(0));

        reviewService.updateReviewDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION");

        verify(reviewRepository, times(1)).updateDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION");
    }


    @Ignore
    @Test
    public void updateReviewScoreTest(){
//        when(reviewRepository.updateScore(users.get(0).getId(),games.get(0).getId(),1)).thenReturn(reviews.get(0));
//
//        reviewService.updateReviewScore(users.get(0).getId(),games.get(0).getId(),1);
//
//        verify(reviewRepository, times(1)).updateScore(users.get(0).getId(),games.get(0).getId(),1);
        Review newReview = new Review("hiugyuvovy",0,games.get(0),users.get(0));
        //Optional<Review> existReview = Optional.of(newReview);
        doReturn(newReview).when(spyreviewRepo).updateScore(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getScore());
        when(reviewRepository.updateScore(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getScore())).thenReturn(newReview);

        reviewService.updateReviewScore(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getScore());
        assertNotEquals(reviews.get(0),newReview);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateReviewScoreTestNotPresent(){
        when(reviewRepository.updateScore(users.get(0).getId(),games.get(0).getId(),1)).thenReturn(reviews.get(0));

        reviewService.updateReviewScore(users.get(0).getId(),games.get(0).getId(),1);

        verify(reviewRepository, times(1)).updateScore(users.get(0).getId(),games.get(0).getId(),1);
    }


    @Ignore
    @Test
    public void deleteReview(){
//        when(reviewRepository.deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId())).thenReturn(reviews.get(0));
//
//        reviewService.deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId());
//
//        verify(reviewRepository, times(1)).deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId());
        Review newReview = new Review("hiugyuvovy",0,games.get(0),users.get(0));
        //Optional<Review> existReview = Optional.of(newReview);
        doReturn(newReview).when(spyreviewRepo).delete(newReview);
        when(reviewRepository.deleteReviewByUserIdAndGameId(newReview.getUser().getId(),newReview.getGame().getId())).thenReturn(newReview);

        reviewService.deleteReviewByUserIdAndGameId(newReview.getUser().getId(),newReview.getGame().getId());
        assertNotEquals(reviews.get(0),newReview);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteReviewNotPresent(){
        when(reviewRepository.deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId())).thenReturn(reviews.get(0));

        reviewService.deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId());

        verify(reviewRepository, times(1)).deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId());

    }
    //Other possible cases

    @Test
    public void testifReviewisNULL(){
        Review testReview = new Review();
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifReviewisNotNULL(){
        Review testReview = new Review("IIIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifGameisNULL(){
        Review testReview = new Review("IIIII",3,null,users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifGameisNotNULL(){
        Review testReview = new Review("IIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifScoreisNegOne(){
        Review testReview = new Review("IIIII",-1, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifScoreisGreater5(){
        Review testReview = new Review("IIIII",6, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifScoreisBetweenZeroAndFive(){
        Review testReview = new Review("IIIII",5, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifUserisNull(){
        Review testReview = new Review("IIIII",3, games.get(0), null);
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifUserisNotNull(){
        Review testReview = new Review("IIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifReivewisTrue(){
        Review testReview = new Review("IIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }



}