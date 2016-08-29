package edu.feicui.okhttpdemo;

import java.util.List;

/**
 * Created by admin on 2016/8/2.
 */
public class Person  {
    /**
     * name : Tom
     * sex : boy
     */
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }


    public static class ListBean {
        private String name;
        private String sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }


    }

    @Override
    public String toString() {
        return "Person{" +
                "list=" + list +
                '}';
    }
}