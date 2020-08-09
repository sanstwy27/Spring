package com.atsanstwy27;

import com.atsanstwy27.impl.MyMathCalculator;
import com.atsanstwy27.inter.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest2 {

    /**
     * 從目前來看，我們的目標是：如何將LogUtils類（切面類）中的這些方法（通知方法）
     * 動態地在目標方法運行的各個位置切入。
     * <p>
     * AOP的使用步驟：
     * 1）導包；（以後把這些都導進來）
     * Spring中支持面向切面編程的包：aspect （基礎版的）
     * 加強版面向切面編程的包（即使目標對像沒有實現任何接口也能創建動態代理）：
     * com.springsource.net.sf.cglib-2.2.0.jar
     * com.springsource.org.aopalliance-1.0.0.jar
     * com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
     * <p>
     * 2）寫配置；
     * １將目標類和切面類(封裝了通知方法(在目標方法執行前後執行的方法))加入到ioc容器中
     * ：
     *
     * @Controller:控制器：我們推薦給控制器層(servlet包下)的組件加這個註解
     * @Service:業務邏輯：我們推薦給業務邏輯層(service包下)的組件加這個註解
     * @Repository:給數據庫層(持久化層，dao層)的組件添加這個註解
     * @Component:給不屬於以上三層的組件加這個註解 （也就是說不要忘記給這些類加一個註解，以便加入到ioc容器中）
     * （加了註解之後，不要忘記在applicationContext中，對這些註解進行掃瞄，以便
     * 加入到ioc容器中（不要忘記在namespace中打開context））
     * <p>
     * ２還應該告訴Spring到底哪個是切面類 （用註解：@Aspect）
     * ３告訴Spring，切面類裡面的每一個方法，都是何時何地運行的
     * ：這幾個註解叫做通知註解，共有5個
     * @Before:在目標方法之前    （前置通知）
     * @After:在目標方法結束之後    （後置通知）
     * @AfterReturning:在目標方法正常返回之後    （返回通知）
     * @AfterThrowing:在目標方法拋出異常之後    （異常通知）
     * @Around:環繞 （環繞通知）
     * <p>
     * 根據SpringAOP_01中的proxy文件，可以得到如下結論：（註解應該何時加）
     * try {
     * @Before result = method.invoke(calculator,args);
     * @AfterReturning } catch (Exception e) {
     * @AfterThrowing }finally{
     * @After }
     * <p>
     * 還需要寫切入表達式，以便告訴Spring，每個方法的切入點
     * 格式：execution(訪問權限符  返回值類型  方法全類名)
     * 如：
     * @Before("execution(public int com.guigu.impl.MyMathCalculator.*(int, int))")
     * <p>
     * ４開啟基於註解的AOP模式
     * （詳情見applicationContext.xml）
     * <p>
     * 3）測試；如下
     */

    ApplicationContext ioc = new ClassPathXmlApplicationContext("conf/aop2.xml");

    @Test
    public void test() {
        //1、從ioc容器中拿到目標對象。注意：如果想要用類型，一定用它的接口類型，不要用它本類
        /**細節1：
         *  輸出結果：
         *  com.guigu.impl.MyMathCalculator@1a3f495
         class $Proxy12
         *  AOP的底層就是動態代理，容器中保存的組件是它的代理對像:$Proxy12
         *  所以這就是為什麼一定要用接口類型，因為在SpringAOP_01中可以知道
         *  要完成動態代理，就需要它是接口類型。
         *
         *  注意：接口最好不加在容器中（沒必要），但實際上也可以加，
         *  加了也不創建對象，只要一個這個組件是一個接口，相當於告訴Spring，
         *	ioc容器中可能有這種類型的組件。
         * */
        /*
        Calculator bean = ioc.getBean(Calculator.class);
        bean.add(2, 1);
        System.out.println(bean);
        System.out.println(bean.getClass());
         */

        //2、通過id拿到目標對像（id默認是類名首字母小寫）
        //Calculator bean2=(Calculator)ioc.getBean("myMathCalculator");
        //System.out.println(bean2.getClass());

        //3、如果MyMathCalculator沒有實現接口：（用本類類型）
        /**
         * 現在沒有實現接口也能完成動態代理：
         * 這是因為cglib幫我們創建好的代理對像
         * */
        MyMathCalculator bean3 = ioc.getBean(MyMathCalculator.class);
        //或者：MyMathCalculator bean3 = (MyMathCalculator)ioc.getBean("myMathCalculator");
        bean3.add(3, 5);
        System.out.println(bean3.getClass());
    }

    /**
     * 細節2：切入點表達式的寫法
     * 固定格式不變：execution(訪問權限符 返回值類型 方法全類名)
     *
     * 通配符：
     * 		*
     * 			１匹配一個或者多個字符
     * 			如：execution(public int com.guigu.impl.MyMath*r.*(int, int))
     * 			２匹配任意一個參數（因為有可能對方法進行了重載）
     * 			（即第一個參數int類型，第二個參數任意類型）
     * 			如：execution(public int com.guigu.impl.MyMath*r.*(int, *))
     * 			３只能匹配一層路徑
     * 			如：execution(public int com.guigu.*.MyMath*r.*(int, *))
     * 			４權限位置*不能用，如果想代表所有權限，直接不寫就行
     * 				也就是說 public可以寫，也可以不寫
     * 			５返回值位置可以用*，代表任意返回值類型
     * 		..
     * 			１匹配任意多個參數，任意類型參數
     * 			如：execution(public int com.guigu.impl.MyMath*r.*(..))
     * 			２可以匹配多層路徑
     * 			如：execution(public int com.guigu..MyMath*r.*(int, *))
     *
     * 總結來說，2種：
     * 最模糊的： execution(* *.*(..))
     * 	第一個*：任意返回值類型
     *  第二個*：任意包，任意類
     *  第三個*：任意方法
     *  （顯然最模糊的 不能用）
     *
     * 最精確的：
     * execution(public int com.guigu.impl.MyMathCalculator.add(int, int))
     *
     * 其他用法："&&" "||" "!"
     *
     * &&:我們要切入的位置滿足這兩個表達式
     * 如：
     execution(public int com.guigu..MyMath*.*(..))&& execution(* *.*(int,int))
     * 這樣一來：MyMathCalculator.add(int,double) 就不能使用
     *
     * ||:我們要切入的位置滿足任意一個表達式
     *
     * !:我們要切入的位置只要不是這個表達式
     * */

    /**
     * 細節3：通知方法的執行順序
     * <p>
     * try {
     *
     * @Before result = method.invoke(calculator,args);
     * @AfterReturning } catch (Exception e) {
     * @AfterThrowing }finally{
     * @After }
     * 以上為理應順序，但是實際如下：
     * 正常執行：@Before => @After => @AfterReturning
     * 異常執行：@Before => @After => @AfterThrowing
     */
    @Test
    public void test02() {
        MyMathCalculator bean = ioc.getBean(MyMathCalculator.class);
        bean.add(3, 5);
        System.out.println("==============");
        bean.div(6, 0);
    }

    /**
     * 細節4：在通知方法運行的時候，拿到目標方法的詳細信息
     *
     * 1）只需要為通知方法的參數列表上寫一個參數joinPoint
     * 		JoinPoint joinPoint，封裝了當前目標方法的詳細信息
     * 詳情見LogUtils
     * 2）用result來接受返回值
     * 詳情見LogUtils
     * 3）用exception來接受異常返回值
     * 詳情見LogUtils
     *
     * 為什麼能這麼用？
     * 因為Spring對通知方法的要求不嚴格
     * 比如我可以改：private int logEnd(JoinPoint joinPoint){}
     *
     * 但是唯一要求的就是方法的參數列表一定不能亂寫。
     * 因為通知方法時Spring利用反射調用的，每次方法調用得確定這個方法的參數表的值。
     * 參數表上的每一個參數，Spring都得知道是什麼。
     * JoinPoint：認識
     * 也就是說，不知道的參數一定要告訴Spring這是什麼
     * */

    /**
     * AOP使用場景：
     * （1）AOP加日誌保存到數據庫；
     * （2）AOP做權限驗證；
     * （3）AOP做安全檢查；
     * （4）AOP做事務控制；
     * */

}
