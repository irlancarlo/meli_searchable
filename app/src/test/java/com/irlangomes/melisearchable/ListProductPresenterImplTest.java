package com.irlangomes.melisearchable;

import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.model.ResultProduct;
import com.irlangomes.melisearchable.presenter.ListProduct;
import com.irlangomes.melisearchable.presenter.ListProductPresenterImpl;

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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ListProductPresenterImplTest {

    @Mock
    private ListProduct.ListProductView view;

    private ListProduct.ListProductPresenter presenter;

    @Mock
    private MeliService meliService;

    private static final String PRODUCT_NAME = "moto g";

    @Before
    public void setUp() {
        presenter = new ListProductPresenterImpl(view, meliService);
    }

    @Test
    public void findProductTestSuccess() {
        // scenario
        ResultProduct resultProduct = new ResultProduct();
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.id = "MLA909780018";
        product.title = "Moto G9 Power 128 Gb Verde Granito 4 Gb Ram";
        product.setPrice("35799");
        product.thumbnail = "http://http2.mlstatic.com/D_650272-MLA44879924681_022021-I.jpg";
        productList.add(product);
        resultProduct.results = productList;

        Call<ResultProduct> resultProductCall = mock(Call.class);
        when(meliService.findProduct(PRODUCT_NAME)).thenReturn(resultProductCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<ResultProduct> callback = invocation.getArgument(0);
                callback.onResponse(resultProductCall, Response.success(resultProduct));
                return null;
            }
        }).when(resultProductCall).enqueue(any(Callback.class));

        // action
        presenter.findProduct(PRODUCT_NAME);

        // validation
        verify(meliService).findProduct(PRODUCT_NAME);
        verify(view).displayProduct(resultProduct.results);
        verify(view).setGoneProgressBar();
    }

    @Test
    public void findProductTestSuccessWithoutBody() {
        // scenario
        ResultProduct resultProduct = null;

        Call<ResultProduct> resultProductCall = mock(Call.class);
        when(meliService.findProduct(PRODUCT_NAME)).thenReturn(resultProductCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<ResultProduct> callback = invocation.getArgument(0);
                callback.onResponse(resultProductCall, Response.success(resultProduct));
                return null;
            }
        }).when(resultProductCall).enqueue(any(Callback.class));

        // action
        presenter.findProduct(PRODUCT_NAME);

        // validation
        verify(view).setGoneProgressBar();
        verify(view).displayEmptySearchMsg();
    }

    @Test
    public void findProductTestSuccessEmptyList() {
        // scenario
        ResultProduct resultProduct = new ResultProduct();

        Call<ResultProduct> resultProductCall = mock(Call.class);
        when(meliService.findProduct(PRODUCT_NAME)).thenReturn(resultProductCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<ResultProduct> callback = invocation.getArgument(0);
                callback.onResponse(resultProductCall, Response.success(resultProduct));
                return null;
            }
        }).when(resultProductCall).enqueue(any(Callback.class));

        // action
        presenter.findProduct(PRODUCT_NAME);

        // validation
        verify(view).setGoneProgressBar();
        verify(view).displayEmptySearchMsg();
    }

    @Test
    public void findProductTestError() {
        // scenario
        ResultProduct resultProduct = new ResultProduct();
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.id = "MLA909780018";
        product.title = "Moto G9 Power 128 Gb Verde Granito 4 Gb Ram";
        product.setPrice("35799");
        product.thumbnail = "http://http2.mlstatic.com/D_650272-MLA44879924681_022021-I.jpg";
        productList.add(product);
        resultProduct.results = productList;

        Call<ResultProduct> resultProductCall = mock(Call.class);
        ResponseBody body = mock(ResponseBody.class);
        when(meliService.findProduct(PRODUCT_NAME)).thenReturn(resultProductCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<ResultProduct> callback = invocation.getArgument(0);
                callback.onResponse(resultProductCall, Response.error(404, body));
                return null;
            }
        }).when(resultProductCall).enqueue(any(Callback.class));

        // action
        presenter.findProduct(PRODUCT_NAME);

        // validation
        verify(view).setGoneProgressBar();
        verify(view).displayEmptySearchMsg();
    }

    @Test
    public void findProductTestFailure() {
        // scenario
        Call<ResultProduct> resultProductCall = mock(Call.class);
        when(meliService.findProduct(PRODUCT_NAME)).thenReturn(resultProductCall);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Callback<ResultProduct> callback = invocation.getArgument(0);
                callback.onFailure(resultProductCall, new Throwable());
                return null;
            }
        }).when(resultProductCall).enqueue(any(Callback.class));

        // action
        presenter.findProduct(PRODUCT_NAME);

        // validation
        verify(meliService).findProduct(PRODUCT_NAME);
        verify(view).setGoneProgressBar();
        verify(view).displayErrorMsg();
    }

    @After
    public void tearDown() {
        presenter.destroyView();
    }
}
