package com.github.matheuswwwp.dinenow.conf.exception.customException.order;

public class PriceNotExcepted extends Exception {
    public PriceNotExcepted() { super("expected price not match"); }
}
