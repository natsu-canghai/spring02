package cn.edu.scujcc.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scujcc.model.Channel;
import cn.edu.scujcc.service.ChannelService;

@RestController
@RequestMapping("/channel")
public class ChannelContrller {
	public static final Logger logger = (Logger) LoggerFactory.getLogger(ChannelContrller.class);
	
	
	@Autowired
	public ChannelService service;
    
		@GetMapping
		public List<Channel> getAllChannels(){
			logger.info("���ڲ������е�Ƶ����Ϣ");
			List<Channel>result = service.getAllChannels();
			logger.debug("����Ƶ���������ǣ�"+result.size());
			return result;
		}
		
		@GetMapping("/{id}")
		public Channel getChannels(@PathVariable String id) {
			//System.out.println("��ȡƵ����id="+id);
			Channel c = service.getChannel(id);
			if(c != null) {
				return c;
			}else {
				logger.error("�Ҳ���ָ����Ƶ����");
				return null;
			}
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<String> deleteChannels(@PathVariable String id) {
			System.out.println("����ɾ��Ƶ����id="+id);
			boolean result = service.deleteChannel(id);
			if(result) {
				return ResponseEntity.ok().body("ɾ���ɹ�");
			}else {
				return ResponseEntity.ok().body("ɾ��ʧ��");
			}
		}
		
		@PostMapping
		public Channel createChannel(@RequestBody Channel c) {
			System.out.println("�����½�Ƶ����Ƶ������"+c);
			Channel saved = service.createChannel(c);
			return saved;
		}
		
		@PutMapping
		public Channel updateChannel(@RequestBody Channel c) {
			System.out.println("��������Ƶ����Ƶ������"+c);
			Channel update = service.updateChannel(c);
			return update;
		}
		
		@GetMapping("/q/{quality}")
		public List<Channel> searchByQuality(@PathVariable String quality){
	    	return service.searchByQuality(quality);
	    }
		
		@GetMapping("/t/{title}")
		public List<Channel> searchByTitle(@PathVariable String title){
			return service.searchByTitle(title);
		}
		@GetMapping("/cold")
		public List<Channel> getColdChannel(){
			return service.findColdChannels();
		}
		@GetMapping("/p/{page}")
		public List<Channel> getColdChannelsPage(@PathVariable int page){
			return service.findChannelsPage(page);
		}
}

