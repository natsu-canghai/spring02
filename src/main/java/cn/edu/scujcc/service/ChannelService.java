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
    	//模拟生成10条频道数据
    	channels = new ArrayList<>();
    	for (int i = 0;i < 10; i++) {
    		Channel c = new Channel();
 //   		c.setId(i+1);
    		c.setTitle("中央"+(i+1)+"台");
    		c.setUrl("http://www.cctv.com");
    		channels.add(c);
    	}
    }
   /**
    * 获取所有频道的数据
    * @return 频道List
    */
    public List<Channel> getAllChannels(){
		//return this.channels;
    	return repo.findAll();
	}
    /**
     * 获取一个频道的数据
     * @param channelId 频道编号
     * @return 频道对象，若未找到则返回null
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
    	//找到目前最大的id，并增加1作为新频道的id
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
    	
    //TODO 仅修改用户指定的属性    	
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
    //FIXME 把新评论追加到老的评论后面
    if(c.getComment() != null) { 
    	if (saved.getComment() != null) {
    	saved.getComment().addAll(c.getComment());
    	}else{ //用新的评论替代老的评论
        	saved.setComments(c.getComment());
        	}
    	return repo.save(saved);//保存更新后的实体对象
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

 