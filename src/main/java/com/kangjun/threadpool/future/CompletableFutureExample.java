package com.kangjun.threadpool.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * 异步Future
 */
@Slf4j
public class CompletableFutureExample {
    public static void main(String[] args) throws Exception{

//        CompletableFuture<String> completableFuture = new CompletableFuture<String>();
//        String result = completableFuture.get();
//        completableFuture.complete("Future's Result Here Manually");

        //Runnable 函数式表达式  不会获取到结果
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
//            System.out.println("运行在一个单独的线程当中");
//        });
//        future.get();

        // Supplier 函数式表达式  可以获得返回结果
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
//            log.info("运行在一个单独的线程当中");
//            return "我有返回值";
//        });
//
//        log.info(future.get());

        //串行的后续操作并不一定会和前序操作使用同一个线程
//        CompletableFuture<String> comboText = CompletableFuture.supplyAsync(() -> {
//            //可以注释掉做快速返回 start
////            try {
////                TimeUnit.SECONDS.sleep(3);
////            } catch (InterruptedException e) {
////                throw new IllegalStateException(e);
////            }
////            log.info("👍");
////            //可以注释掉做快速返回 end
//            return "赞";
//            })
//                .thenApply(first -> {
//                    log.info("在看");
//                    return first + ", 在看";
//                })
//                .thenApply(second -> second + ", 转发");
//
//        log.info("三连有没有？");
//        log.info(comboText.get());

        //
        final CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(
                // 模拟远端API调用，这里只返回了一个构造的对象
                () -> Product.of(12345L,"颈椎/腰椎治疗仪"))
                .thenAccept(product -> {
                    log.info("获取到远程API产品名称 " + product.getName());
                });
        voidCompletableFuture.get();

    }
}
