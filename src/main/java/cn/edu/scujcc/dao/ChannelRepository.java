package cn.edu.scujcc.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.edu.scujcc.model.Channel;

public interface ChannelRepository extends MongoRepository<Channel, String>{
	public List<Channel> findByQuality(String q);
	public Channel findFirstByQuality(String q);
	public List<Channel> findByTitle(String t);
	
	//�ҳ�������û�����۵�Ƶ��
	public List<Channel> findByCommentsNull();
	
}
