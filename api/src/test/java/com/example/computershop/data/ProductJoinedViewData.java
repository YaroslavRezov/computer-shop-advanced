package com.example.computershop.data;

import com.example.computershop.model.entity.ProductJoinedView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductJoinedViewData {

    public static ProductJoinedView createProductJoinedViewMock1() {
        ProductJoinedView product = mock(ProductJoinedView.class);
        when(product.getModel()).thenReturn("1232");
        when(product.getMaker()).thenReturn("A");
        when(product.getType()).thenReturn("PC");
        when(product.getCode()).thenReturn(1L);
        return product;
    }

    public static ProductJoinedView createProductJoinedViewMock2() {
        ProductJoinedView product = mock(ProductJoinedView.class);
        when(product.getModel()).thenReturn("b276a11d-c526-4f74-b3c7-95ff94bf7147");
        when(product.getMaker()).thenReturn("bbbruu");
        when(product.getType()).thenReturn("PC");
        when(product.getCode()).thenReturn(29L);
        return product;
    }
}
