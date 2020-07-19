package concurrencytools.threadlocal;

/**
 * 通过ThreadLocal实现避免参数传递的麻烦
 * 在很多实际开发中，各服务或各方法存在参数传递，会导致代码的冗余且不易维护
 * 如果设置一个全局的资源可会引发线程安全等问题，
 * 可以通过加锁或concurrentHashMap等方式保证线程安全但同时也会带来性能问题
 * 而使用ThreadLocal就避免的参数传递，同时也保证了并发安全，而且也不会影响程序的性能
 */
public class ThreadLocalNormalUsage10 {
    public static void main(String[] args) {
        new Service1().process();
    }
}
/**
 * 用于生成user对象，对应于实际开发中的过滤器/拦截器，所有的请求都会先进入该类
 * 做完一些处理后调用其它服务并传递用户信息
 */
class Service1 {
    public void process() {
        User user = new User("超哥");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}
/**
 * 服务2，需要拿到用户信息做一些业务处理，处理完成后传递给下一个服务
 */
class Service2 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service2拿到用户名：" + user.name);
        // call next service
        new Service3().process();
    }
}
/**
 * 服务3，需要拿到用户信息做一些业务处理，处理完成后传递给下一个服务
 */
class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名：" + user.name);
        // call next service
        // new ServiceN ...
        // 假设这是业务中的最后一个服务，后面不会再调用ThreadLocal
        // 此时应该手动删除该ThreadLocal，避免value的内存泄漏，详见笔记
        UserContextHolder.holder.remove();
    }
}
class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}
class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}