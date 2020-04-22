package cn.edu.scujcc.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.edu.scujcc.dao.ChannelRepository;
import cn.edu.scujcc.model.Channel;

@Service
public class ChannelService {
	@Autowired
	private ChannelRepository repo;
    private List<Channel> channels;
    
    public  ChannelService() {
    	//ģ������10��Ƶ������
    	channels = new ArrayList<>();
    	for (int i = 0;i < 10; i++) {
    		Channel c = new Channel();
 //   		c.setId(i+1);
    		c.setTitle("����"+(i+1)+"̨");
    		c.setUrl("http://www.cctv.com");
    		channels.add(c);
    	}
    }
   /**
    * ��ȡ����Ƶ��������
    * @return Ƶ��List
    */
    public List<Channel> getAllChannels(){
		//return this.channels;
    	return repo.findAll();
	}
    /**
     * ��ȡһ��Ƶ��������
     * @param channelId Ƶ�����
     * @return Ƶ��������δ�ҵ��򷵻�null
     */
    public Channel getChannel(String channelId) {
    	Optional<Channel> result = repo.findById(channelId);

    		if(result.isPresent()) {
    			return result.get();
    		}
    		else {
    			return null;
    		}
    	}
   
    /**
     * 
     */
    public boolean deleteChannel(String id) {
    	boolean result = true;
    	repo.deleteById(id);
    		
    	return result;
    	}
    
    /**
     * 
     */
    public Channel createChannel(Channel c) {
    	//�ҵ�Ŀǰ����id��������1��Ϊ��Ƶ����id
    	//int newId = channels.get(channels.size()-1).getId()+1;
    	//c.setId(newId);
    	//channels.add(c);
    	//return c;
    	return repo.save(c);
    }
    
    public List<Channel> searchByQuality(String quality){
    	return repo.findByQuality(quality);
    }
    
    public List<Channel> searchByTitle(String title){
    	return repo.findByTitle(title);
    }
    
    public Channel updateChannel(Channel c) {
    //	Channel toUpdate = getChannel(c.getId());
    //	if(toUpdate != null) {
    //		toUpdate.setTitle(c.getTitle());
    //		toUpdate.setQuality(c.getQuality());
    //		toUpdate.setUrl(c.getUrl());
    //	}
    //	return toUpdate;
    	//return null
    	
    //TODO ���޸��û�ָ��������    	
    Channel saved = getChannel(c.getId());
    if(c.getTitle() != null) { 
    	saved.setTitle(c.getTitle());
    	}
    if(c.getQuality() != null) { 
    	saved.setQuality(c.getQuality());
    	}
    if(c.getUrl() != null) { 
    	saved.setUrl(c.getUrl());
    	}
    //FIXME ��������׷�ӵ��ϵ����ۺ���
    if(c.getComment() != null) { 
    	if (saved.getComment() != null) {
    	saved.getComment().addAll(c.getComment());
    	}else{ //���µ���������ϵ�����
        	saved.setComments(c.getComment());
        	}
    	return repo.save(saved);//������º��ʵ�����
        }
	return saved;
    }
    public List<Channel> findColdChannels(){
    	return repo.findByCommentsNull();
    }
    
    public List<Channel> findChannelsPage(int page){
    	Page<Channel> p = repo.findAll(PageRequest.of(page,3));
    	return p.toList();
    }
}

 