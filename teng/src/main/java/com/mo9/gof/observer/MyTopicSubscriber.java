package com.mo9.gof.observer;

public class MyTopicSubscriber implements Observer {

	private String name;
	private Subject topic;

	public MyTopicSubscriber(String nm){
		this.name=nm;
	}
	/**
	 * update()方法的实现使用了被观察�?�的getUpdate()来处理更新的消息,
	 * 此处应该避免把消息作为参数传递给update()方法�?
	 * @author beiteng
	 *
	 */
	@Override
	public void update() {
		String msg = (String) topic.getUpdate(this);
		if(msg == null){
			System.out.println(name+":: No new message");
		}else
		System.out.println(name+":: Consuming message::"+msg);
	}

	@Override
	public void setSubject(Subject sub) {
		this.topic=sub;
	}

}