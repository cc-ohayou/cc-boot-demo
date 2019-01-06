package com.cc.ccbootdemo.core.test.generic;

import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.*;

/**
 * @AUTHOR CF  泛型这个 T 可以使任何大写字母 意思都一样
 * 这个T表示派生自Object类的任何类
 * 泛型的优势
 * （1）、不用强制转换
 * (2)、在settVar()时如果传入类型不对，编译时会报错
 * @DATE Created on 2018/12/11 10:41.
 */
@Data
public class Point<T> {
    private T x;
    private T y;


    public static void main(String[] args) {

//IntegerPoint使用
        Point<Integer> p = new Point<>();
        p.setX(100);
        System.out.println(p.getX());

//FloatPoint使用
        //
        Point<Float> p2 = new Point<>();
        p2.setX(100.12f);
        System.out.println(p.getX());
        PointNotGeneric generic = new PointNotGeneric();
        generic.setX(100.12f);
        /*//不使用泛型 此处编译时不会报错  但运行时会报错
        // 因为x实际的值是Float类型 强制转换成String必然会报错
        // 泛型解决的就是这个问题   我们在  Point<Integer> p = new Point<>() ;
        // 初始化时制定泛型的类型 这样下面获取值的时候 如果不符合语法就可以被检查出来
        譬如此处  Point<Integer> p = new Point<>() ;
        p.setX(100) ;
        没什么问题
        但如果传入  p.setX(100.2f) ;  则会编译通不过
        泛型既可以让我们灵活的传入我们想要处理的数据 也可以在编译期检查出语法问题 皆大欢喜
        */

//        String s= (String) generic.getX();//运行时错误


//        showGenericSuperClassInfoTest();
//        showGenericSuperInterfaceInfoTest();
//        genericVariableInfoTest();
//        genericArrayTypeInfoTest();
        parseClass(PointImpl.class);
        System.out.println("------");
        parseClass(PointArrayImpl.class);
        System.out.println("------");

        parseClass(PointGenericityImpl.class);
    }

    /**
     *
     */
    private static void genericArrayTypeInfoTest() {

        Class<?> clazz = PointArrayImpl.class;
        Type[] interfaces = clazz.getGenericInterfaces();
        for (Type type:interfaces){
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                Type[] actualArgs = pt.getActualTypeArguments();
                for (Type arg:actualArgs){

                    if (arg instanceof GenericArrayType){
                        GenericArrayType arrayType = (GenericArrayType)arg;
                        Type comType = arrayType.getGenericComponentType();
                        Class<?> typeClass = (Class)comType;
                        System.out.print("数组类型为："+typeClass.getName());
                    }else if(arg instanceof Class){
                        Class<?> typeClass = (Class)arg;
                        System.out.print("Class类型："+typeClass.getTypeName());
                        System.out.print("Class类型："+typeClass.getSimpleName());
                    }else{
                        Class<?> typeClass = (Class)arg;
                        System.out.print("实际类型："+typeClass.getSimpleName());
                    }

                }
            }
        }

    }

    /**
     * @description  实现父类时 为泛型指定的是 泛型变量时
     * @author CF create on 2018/12/12 15:28
     */
    private static void genericVariableInfoTest() {

        Class<?> clazz = PointGenericityImpl.class;
        Type[] types = clazz.getGenericInterfaces();
        for (Type type:types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                //返回表示此类型实际类型参数的 Type 对象的数组
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type parameterArgType : actualTypeArguments) {
//                    type代表的类型是一个泛型变量时，它的类型就是TypeVariable
                    if (parameterArgType instanceof TypeVariable) {
                        //是泛型变量 那么看下实现类对这个泛型变量有没有指定绑定边界 也即 T extends Number 之类
                        TypeVariable typeVariable = (TypeVariable) parameterArgType;
                        System.out.println("此接口的填充类型为：" + typeVariable.getName());

                        //返回表示此类型变量上边界的 Type 对象的数组。
                        Type[] typebounds = typeVariable.getBounds();
                        for (Type bound : typebounds) {
                            Class<?> boundClass = (Class) bound;
                            //如果不写，则默认输出Object，如果写了，则输出对应的
                            System.out.println("bound为：" + boundClass.getName());
                        }
                    }
                    //如果是Class 类型 说明指明了泛型的具体类型譬如 Integer之类的 直接输出
                    //此处 PointGenericityImpl<T extends Number&Serializable>
                    // implements PointInterface<T,Integer>
                    //可以看到 对T这个泛型 实现的是 泛型变量
                    // 第二个泛型直接指定为了Integer符合此处的条件   instanceof Class
                    if (parameterArgType instanceof Class) {
                        Class parameterArgClass = (Class) parameterArgType;
                        System.out.println("此接口的填充类型为：" + parameterArgClass.getName());
                    }


                }

            }

        }
    }

    /**
     * @description 获取所继承泛型接口的相关信息
     * 实现父类时 为泛型指定的是具体的类型时
     * @author CF create on 2018/12/12 15:01
     */
    private static void showGenericSuperInterfaceInfoTest() {
        Class<?> clazz = PointImpl.class;
        //Type是一个接口，这里意思是它是Java所有类型都会继承这个接口   Class继承了这个接口
        // Type类型是泛型所特有的 是用来标识，当前Class中所填充的类型的
        Type[] types = clazz.getGenericInterfaces();

        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                //ParameterizedType对应泛型类型 譬如 此处PointImpl的父类Point即为泛型类型
                ParameterizedType parameterizedType = (ParameterizedType) type;
                //返回表示此类型实际类型参数的 Type 对象的数组   也就是指定的泛型类型
            /*用来返回当前泛型表达式中，用来填充泛型变量的真正值的列表
             但并不是所有的每次都会返回填充类型对应的Class对象
             虽然我们没办法穷举可能填充为哪些类型，但我们知道Type类型是用来表示填充泛型变量的类型的，
             而继承Type接口只有下面五个：Class,ParameterizedType,TypeVariable,WildcardType,GenericArrayType!
            所以这也是Type[] getActualTypeArguments();中Type[]数组的所有可能取值！*/

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type parameterArgType : actualTypeArguments) {
                    //泛型的填充类型必须是类Integer这种 此处获取填充类型的Class对象 进而获取其全名
                    Class parameterArgClass = (Class) parameterArgType;
                    //java.lang.Integer 参考
                    System.out.println("此接口的填充类型为：" + parameterArgClass.getName());
                }

                //返回 Type 对象，表示声明此类型的类或接口。 声明当前泛型表达式的类或者接口的Class对象 此处是Point
                Type type1 = parameterizedType.getRawType();
                Class class22 = (Class) type1;
                System.out.println("声明此接口的类型为：" + class22.getName());
            }
        }
    }

    /**
     * @description 获取泛型超类相信信息
     * @author CF create on 2018/12/12 15:00
     */
    private static void showGenericSuperClassInfoTest() {
        Class<?> clazz = PointImpl.class;
        //Type是一个接口，这里意思是它是Java所有类型都会继承这个接口   Class继承了这个接口
        // Type类型是泛型所特有的 是用来标识，当前Class中所填充的类型的
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            //ParameterizedType对应泛型类型 譬如 此处PointImpl的父类Point即为泛型类型
            ParameterizedType parameterizedType = (ParameterizedType) type;
            //返回表示此类型实际类型参数的 Type 对象的数组   也就是指定的泛型类型
            /*用来返回当前泛型表达式中，用来填充泛型变量的真正值的列表
             但并不是所有的每次都会返回填充类型对应的Class对象
             虽然我们没办法穷举可能填充为哪些类型，但我们知道Type类型是用来表示填充泛型变量的类型的，
             而继承Type接口只有下面五个：Class,ParameterizedType,TypeVariable,WildcardType,GenericArrayType!
            所以这也是Type[] getActualTypeArguments();中Type[]数组的所有可能取值！*/

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type parameterArgType : actualTypeArguments) {
                //泛型的填充类型必须是类Integer这种 此处获取填充类型的Class对象 进而获取其全名
                Class parameterArgClass = (Class) parameterArgType;
                //java.lang.Integer 参考
                System.out.println("填充类型为：" + parameterArgClass.getName());
            }

            //返回 Type 对象，表示声明此类型的类或接口。 声明当前泛型表达式的类或者接口的Class对象 此处是Point
            Type type1 = parameterizedType.getRawType();
            Class class22 = (Class) type1;
            System.out.println("PointImpl的父类类型为：" + class22.getName());

        }

    }


    static class PointNotGeneric {
        private Object x;
        private Object y;

        public Object getX() {
            return x;
        }

        public void setX(Object x) {
            this.x = x;
        }

        public Object getY() {
            return y;
        }

        public void setY(Float y) {
            this.y = y;
        }


    }

    public static class PointImpl extends Point<Integer> implements PointInterface<String,Integer>{

    }

    public interface PointInterface<T, U> {

    }

    /**
     * type代表的类型是一个泛型变量时，它的类型就是TypeVariable
     * PointInterface的基础上，重写一个类PointGenericityImpl，与上面直接在类中填充不同的是，它是一个泛型类
     *
     */
    public static class PointGenericityImpl<T extends Number&Serializable> implements PointInterface<T,Integer> {
    }



    public interface PointSingleInterface<T> {
    }

    /**
     * GenericArrayType   当type对应的类型是类似于String[]、Integer[]等的数组时，
     * 那type的类型就是GenericArrayType；  
     * 但 实验证明 不成立了 String[] Integer[]是Class类型 PointArrayImpl为例
     *    getTypeName() 返回 java.lang.String[]  getSimpleName返回的是String[] 
     *    
     * 这里要特别说明的如果type对应的是类似于ArrayList、List这样的类型，
     * 那type的类型应该是ParameterizedType，
     * 而不是GenericArrayType，因为ArrayList是一个泛型表达式
     */
    public static class PointArrayImpl implements PointSingleInterface<String[]> {
    }

    /**
     * 通配符类型
     */
    public class PointWildcardImpl implements PointSingleInterface<Comparable<? extends Number>> {
    }


    private static void parseClass(Class<?> c){
        parseTypeParameters(c.getGenericInterfaces());
    }

    private static void parseTypeParameters(Type[] types) {
        for(Type type:types){
            parseTypeParameter(type);
        }
    }

    private static  void parseTypeParameter(Type type) {

            if(type instanceof Class){
                Class<?> c = (Class<?>) type;
                System.out.println( c.getSimpleName());
            } else if(type instanceof TypeVariable){
                TypeVariable<?> tv = (TypeVariable<?>)type;
                 System.out.println( tv.getName());
                parseTypeParameters(tv.getBounds());
            } else if(type instanceof WildcardType){
                WildcardType wt = (WildcardType)type;
                 System.out.println( "?");
                parseTypeParameters(wt.getUpperBounds());
                parseTypeParameters(wt.getLowerBounds());
            } else if(type instanceof ParameterizedType){
                ParameterizedType pt = (ParameterizedType)type;
                Type t = pt.getOwnerType();
                if(t != null) {
                    parseTypeParameter(t);
                }
                parseTypeParameter(pt.getRawType());
                parseTypeParameters(pt.getActualTypeArguments());
            } else if (type instanceof GenericArrayType){
                GenericArrayType arrayType = (GenericArrayType)type;
                Type t = arrayType.getGenericComponentType();
                parseTypeParameter(t);
            }
        
       
    }
}

