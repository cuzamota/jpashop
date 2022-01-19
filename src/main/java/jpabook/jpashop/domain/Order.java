package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {


    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //XToOne의 FetchType은 모두 LAZY로 설정할 것

    //XToOne의 기본 FetchType = EAGER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //foreign key
    private Member member;

    // JPQL select o from order o -> SQL select * from order = 100 + 1(order)

    //XToMany의 기본 FetchType = LAZY
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

//    기본
//    persist(orderA)
//    persist(orderB)
//    persist(orderC)
//    persist(order)
//
//    cascade = CascadeType.ALL
//    persist(order)

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계편의 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        member.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//
//        //member.getOrders().add(order);
//        order.setMember(member);
//    }


}
