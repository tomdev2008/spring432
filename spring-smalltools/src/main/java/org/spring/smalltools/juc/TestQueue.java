package org.spring.smalltools.juc;

/*
 * 值得注意的是Queue接口本身定义的几个常用方法的区别，
    add方法和offer方法的区别在于超出容量限制时前者抛出异常，后者返回false；
    remove方法和poll方法都从队列中拿掉元素并返回，但是他们的区别在于空队列下操作前者抛出异常，而后者返回null；
    element方法和peek方法都返回队列顶端的元素，但是不把元素从队列中删掉，区别在于前者在空队列的时候抛出异常，后者返回null。
 */
public class TestQueue {

}
