package com.cc.ccbootdemo.core.test.generic;

import lombok.Data;

/**
 * @AUTHOR CF
 * 泛型类实现 复写方法也会自动的将方法参数转换为泛型
 * @DATE Created on 2018/12/11 10:55.
 */
public class GenericInterfaceImpl<T> implements GenericInterface<T>{
    @Override
    public void getVar() {

    }

    @Override
    public T setVar() {
        return null;
    }

    /**
     * 实现类也是泛型类的话方法类型还是跟随 implements GenericInterface<T>这里指定的类型
     *
     */
    class   GenericInterfaceImpl2<U>  implements GenericInterface<T>{

        @Override
        public void getVar() {

        }

        @Override
        public T setVar() {
            return null;
        }
    }
    /**
     * 普通类的话则指定的是什么类型 生成的方法也是什么类型
     *
     */
    class   GenericInterfaceImpl3  implements GenericInterface<T>{

        @Override
        public void getVar() {

        }

        @Override
        public T setVar() {
            return null;
        }
    }
    /**
     * 实现类是一个泛型类 我们
     * 把第三个泛型变量E用来填充接口
     */
    @Data
     class   GenericInterfaceImpl4<K,W,E>  implements GenericInterface<E>{
       private  K x;
       private  W y;
       private  E z;
        @Override
        public void getVar() {

        }

        @Override
        public E setVar() {
            return null;
        }


    }

}
