package com.atguigu.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class RandomRule_ZY extends AbstractLoadBalancerRule {

    private  int total = 0;
    private  int currentIndex = 0;

    public Server choose(ILoadBalancer lb,Object key){

        if (lb ==null){
            return null;
        }
        Server server = null;
        while (server ==null){
            if(Thread.interrupted()){
                return null;
            }
            List<Server> uplist = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();
            int serverCount = allList.size();
            if (serverCount==0){
                return null;
            }
            if(total<5){
                server = uplist.get(currentIndex);
                total++;
            }else{
                total=0;
                currentIndex++;
                if(currentIndex>=uplist.size()){
                    currentIndex=0;
                }
            }
            if (server==null){
                return null;
            }

        }
        return server;
    }


    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        // TODO Auto-generated method stub

    }
}
