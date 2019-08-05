package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ChainPatternDemo {
    private static AbstractValidate getChainOfValidate(){

        AbstractValidate productValidate = new ProductValidate(AbstractValidate.PRODUCT);
        AbstractValidate moduleValidate = new ModuleValidate(AbstractValidate.MODULE);
        AbstractValidate apiValidate = new ApiValidate(AbstractValidate.API);

        productValidate.setNextValidate(moduleValidate);
        moduleValidate.setNextValidate(apiValidate);

        return productValidate;
    }

    public static void main(String[] args) {
        AbstractValidate loggerChain = getChainOfValidate();

       /* loggerChain.check(AbstractValidate.API, "This is a API.");

        loggerChain.check(AbstractValidate.MODULE,
                "This is a MODULE.");*/

        loggerChain.check(AbstractValidate.PRODUCT,
                "This is an PRODUCT.");
    }
}
