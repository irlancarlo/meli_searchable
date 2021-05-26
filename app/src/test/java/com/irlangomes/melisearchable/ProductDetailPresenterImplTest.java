package com.irlangomes.melisearchable;

import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.model.Picture;
import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.model.ResultProduct;
import com.irlangomes.melisearchable.presenter.ListProductPresenterImpl;
import com.irlangomes.melisearchable.presenter.ProductDetail;
import com.irlangomes.melisearchable.presenter.ProductDetailPresenterImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailPresenterImplTest {

    @Mock
    private ProductDetail.ProductDetailView view;

    private ProductDetail.ProductDetailPresenter presenter;

    @Mock
    private MeliService meliService;

    private static final String PRODUCT_ID = "MLA909780018";

    @Before
    public void setUp() {
        presenter = new ProductDetailPresenterImpl(view, meliService);
    }

    @Test
    public void findProductDetailSuccess() {
        // scenario
        List<Picture> pictureList = new ArrayList<>();
        Picture picture = new Picture();
        picture.id = "0001";
        pictureList.add(picture);
        picture.url = "http://http2.mlstatic.com/D_650272-MLA44879924681_022021-I.jpg";
        Product product = new Product();
        product.id = "MLA909780018";
        product.setPrice("212133");
        product.pictures = pictureList;

        Call<Product> resultCall = mock(Call.class);
        when(meliService.findProductDetail(PRODUCT_ID)).thenReturn(resultCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<Product> callback = invocation.getArgument(0);
                callback.onResponse(resultCall, Response.success(product));
                return null;
            }
        }).when(resultCall).enqueue(any(Callback.class));

        // action
        presenter.findProductDetail(PRODUCT_ID);

        // validation
        verify(view).setVisibleProgressBar();
        verify(meliService).findProductDetail(PRODUCT_ID);
    }

    @Test
    public void findProductDetailError() {
        // scenario
        List<Picture> pictureList = new ArrayList<>();
        Picture picture = new Picture();
        picture.id = "0001";
        pictureList.add(picture);
        picture.url = "http://http2.mlstatic.com/D_650272-MLA44879924681_022021-I.jpg";
        Product product = new Product();
        product.id = "MLA909780018";
        product.setPrice("212133");
        product.pictures = pictureList;

        Call<Product> resultCall = mock(Call.class);
        ResponseBody body = mock(ResponseBody.class);
        when(meliService.findProductDetail(PRODUCT_ID)).thenReturn(resultCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<Product> callback = invocation.getArgument(0);
                callback.onResponse(resultCall, Response.error(404, body));
                return null;
            }
        }).when(resultCall).enqueue(any(Callback.class));

        // action
        presenter.findProductDetail(PRODUCT_ID);

        // validation
        verify(view).setGoneProgressBar();
        verify(view).displayError();
    }

    @Test
    public void findProductDetailFailure() {
        // scenario
        List<Picture> pictureList = new ArrayList<>();
        Picture picture = new Picture();
        picture.id = "0001";
        pictureList.add(picture);
        picture.url = "http://http2.mlstatic.com/D_650272-MLA44879924681_022021-I.jpg";
        Product product = new Product();
        product.id = "MLA909780018";
        product.setPrice("212133");
        product.pictures = pictureList;

        Call<Product> resultCall = mock(Call.class);
        when(meliService.findProductDetail(PRODUCT_ID)).thenReturn(resultCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<Product> callback = invocation.getArgument(0);
                callback.onFailure(resultCall, new Throwable());
                return null;
            }
        }).when(resultCall).enqueue(any(Callback.class));

        // action
        presenter.findProductDetail(PRODUCT_ID);

        // validation
        verify(view).setVisibleProgressBar();
        verify(meliService).findProductDetail(PRODUCT_ID);
        verify(view).setGoneProgressBar();
        verify(view).displayError();
    }

    @After
    public void tearDown() {
        presenter.destroyView();
    }

}
