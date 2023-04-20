import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Main {

  private static final Consumer<String> log = System.out::println;

  public static void main(String... args) throws Throwable {
    var main = Fiber.async(() -> {
      var ws0 = callExternalWS("WS-0");
      var ws1 = callExternalWS("WS-1");
      Fiber.await(ws0, ws1);
      var result = ws0.get() + ws1.get();
      log.accept(String.format("[%s] ws0: %s, ws1: %s, result: %s"
        , Thread.currentThread()
        , ws0.get()
        , ws1.get()
        , result));
      return result;
    });
    main.get();
  }

  private static CompletableFuture<Integer> callExternalWS(String label) {
    return Fiber.async(() -> {
      log.accept(String.format("[%s] Running -> %s", Thread.currentThread(), label));
      Fiber.sleep(Duration.ofSeconds(2));
      return new Random().nextInt(100);
    });
  }

}
