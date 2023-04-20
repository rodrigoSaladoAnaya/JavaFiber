import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class Fiber {


  public static void await(CompletableFuture<?>... futures) {
    if (!Thread.currentThread().isVirtual()) {
      throw new RuntimeException("Use Virtual Thread");
    }
    CompletableFuture.allOf(futures).join();
  }

  public static <T> CompletableFuture<T> async(Callable<T> callable) {
    var future = new CompletableFuture<T>();
    Thread.ofVirtual()
      .start(() -> {
        try {
          future.complete(callable.call());
        } catch (Exception e) {
          future.completeExceptionally(e);
        }
      });
    return future;
  }

  public static void sleep(int seconds) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
