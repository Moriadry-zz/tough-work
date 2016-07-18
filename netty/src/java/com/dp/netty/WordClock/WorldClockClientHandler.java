package com.dp.netty.WordClock;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * @author dp
 * @create 2016/7/5
 */
public class WorldClockClientHandler extends SimpleChannelInboundHandler<WorldClockProtocol.LocalTimes> {

    private static final Pattern DELIM = Pattern.compile("/");

    private volatile Channel channel;
    private final BlockingQueue<WorldClockProtocol.LocalTimes> answer = new LinkedBlockingQueue<WorldClockProtocol.LocalTimes>();

    public WorldClockClientHandler() {
        super(false);
    }

    public List<String> getLocalTimes(Collection<String> cities) {
        WorldClockProtocol.Locations.Builder builder = WorldClockProtocol.Locations.newBuilder();

        //构建locations数组消息协议对象
        for (String c: cities) {
            String[] components = DELIM.split(c);
            builder.addLocation(WorldClockProtocol.Location.newBuilder().
                    setContinent(WorldClockProtocol.Continent.valueOf(components[0].toUpperCase())).
                    setCity(components[1]).build());
        }

        channel.writeAndFlush(builder.build());

        WorldClockProtocol.LocalTimes localTimes;
        boolean interrupted = false;
        for (;;) {
            try {
                localTimes = answer.take();//得到处理后的结果集
                break;
            } catch (InterruptedException ignore) {
                interrupted = true;
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        List<String> result = new ArrayList<String>();
        for (WorldClockProtocol.LocalTime lt: localTimes.getLocalTimeList()) {
            result.add(
                    new Formatter().format(
                            "%4d-%02d-%02d %02d:%02d:%02d %s",
                            lt.getYear(),
                            lt.getMonth(),
                            lt.getDayOfMonth(),
                            lt.getHour(),
                            lt.getMinute(),
                            lt.getSecond(),
                            lt.getDayOfWeek().name()).toString());
        }

        return result;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, WorldClockProtocol.LocalTimes times) throws Exception {
        //得到服务器端运算的时间结果集LocalTimes
        answer.add(times);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

