package paging;

public final class PagingUtil {

    public static int getPage(int offset, int limit) {
        return offset / limit;
    }

    public static void main(String[] args) {
        System.out.println(getPage(20, 10));
        System.out.println(getPage(10, 10));
        System.out.println(getPage(0, 10));
        System.out.println(getPage(50, 10));
    }

}
