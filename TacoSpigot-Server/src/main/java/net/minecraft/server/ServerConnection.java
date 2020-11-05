package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import eu.revamp.spigot.config.Settings;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.SpigotConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

public class ServerConnection {

    private static final Logger e = LogManager.getLogger();
    public static final LazyInitVar<NioEventLoopGroup> a = new LazyInitVar() {
        protected NioEventLoopGroup a() {
            return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
        }

        protected Object init() {
            return this.a();
        }
    };
    public static final LazyInitVar<EpollEventLoopGroup> b = new LazyInitVar() {
        protected EpollEventLoopGroup a() {
            return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
        }

        protected Object init() {
            return this.a();
        }
    };
    public static final LazyInitVar<LocalEventLoopGroup> c = new LazyInitVar() {
        protected LocalEventLoopGroup a() {
            return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
        }

        protected Object init() {
            return this.a();
        }
    };
    private final MinecraftServer f;
    public volatile boolean d;
    private final List<ChannelFuture> g = Collections.synchronizedList(Lists.newArrayList());
    private final List<NetworkManager> h = Collections.synchronizedList(Lists.newArrayList());

    public ServerConnection(MinecraftServer minecraftserver) {
        this.f = minecraftserver;
        this.d = true;
    }

    /*
     public void a(InetAddress inetaddress, int i) throws IOException {
        List<ChannelFuture> list = this.g;
      
          synchronized (this.g) {
                 Class<NioServerSocketChannel> oclass = null;
         
                 LazyInitVar<NioEventLoopGroup> lazyinitvar = null;
               if (Epoll.isAvailable() && this.f.ai()) {
                       Class<EpollServerSocketChannel> clazz = EpollServerSocketChannel.class;
                   LazyInitVar<EpollEventLoopGroup> lazyInitVar = b;
                       e.info("Using epoll channel type");
                     } else {
                     oclass = NioServerSocketChannel.class;
                      lazyinitvar = a;
                   e.info("Using default channel type");
                     }
         
              this.g.add((new ServerBootstrap()).channel(oclass).childHandler(new ChannelInitializer() {
                             protected void initChannel(Channel channel) throws Exception {
                                   try {
                                       channel.config().setOption(ChannelOption.TCP_NODELAY, Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.TCP_NO_DELAY);
                                  } catch (ChannelException channelException) {}
                 
                 
                 
                               channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new LegacyPingHandler(ServerConnection.this)).addLast("splitter", new PacketSplitter()).addLast("decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND)).addLast("prepender", new PacketPrepender()).addLast("encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND));
                               NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
               
                        ServerConnection.this.h.add(networkmanager);
                    channel.pipeline().addLast("packet_handler", networkmanager);
                           networkmanager.a(new HandshakeListener(ServerConnection.this.f, networkmanager));
                                 }
                }).group(lazyinitvar.c()).localAddress(inetaddress, i).bind().syncUninterruptibly());
               }
         }*/



    public void a(InetAddress inetaddress, int i) throws IOException {
        List list = this.g;

        synchronized (this.g) {
            Class oclass;
            LazyInitVar lazyinitvar;

            if (Epoll.isAvailable() && this.f.ai()) {
                oclass = EpollServerSocketChannel.class;
                lazyinitvar = ServerConnection.b;
                ServerConnection.e.info("Using epoll channel type");
            } else {
                oclass = NioServerSocketChannel.class;
                lazyinitvar = ServerConnection.a;
                ServerConnection.e.info("Using default channel type");
            }
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.childOption(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 32 * 1024);
            bootstrap.childOption(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 8 * 1024);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.TCP_NO_DELAY);
            
            this.g.add((bootstrap).channel(oclass).childHandler(new ChannelInitializer() {
                protected void initChannel(Channel channel) throws Exception {
                    try {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.TCP_NO_DELAY);
                    } catch (ChannelException channelexception) {
                    }

                    channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new LegacyPingHandler(ServerConnection.this)).addLast("splitter", new PacketSplitter()).addLast("decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND)).addLast("prepender", new PacketPrepender()).addLast("encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND));
                    NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);

                    ServerConnection.this.h.add(networkmanager);
                    channel.pipeline().addLast("packet_handler", networkmanager);
                    networkmanager.a(new HandshakeListener(ServerConnection.this.f, networkmanager));
                }
            }).group((EventLoopGroup) lazyinitvar.c()).localAddress(inetaddress, i).bind().syncUninterruptibly());
        }
    }

    public void b() {
        this.d = false;
        Iterator iterator = this.g.iterator();

        while (iterator.hasNext()) {
            ChannelFuture channelfuture = (ChannelFuture) iterator.next();

            try {
                channelfuture.channel().close().sync();
            } catch (InterruptedException interruptedexception) {
                ServerConnection.e.error("Interrupted whilst closing channel");
            }
        }

    }

    public void c() {
        List list = this.h;

        synchronized (this.h) {
            // Spigot Start
            // This prevents players from 'gaming' the server, and strategically relogging to increase their position in the tick order
            if ((SpigotConfig.playerShuffle > 0) && (MinecraftServer.currentTick % SpigotConfig.playerShuffle == 0) )
            {
                Collections.shuffle( this.h );
            }
            // Spigot End
            Iterator iterator = this.h.iterator();

            while (iterator.hasNext()) {
                final NetworkManager networkmanager = (NetworkManager) iterator.next();

                if (!networkmanager.h()) {
                    if (!networkmanager.g()) {
                        // Spigot Start
                        // Fix a race condition where a NetworkManager could be unregistered just before connection.
                        if (networkmanager.preparing) continue;
                        // Spigot End
                        iterator.remove();
                        networkmanager.l();
                    } else {
                        try {
                            networkmanager.a();
                        } catch (Exception exception) {
                            if (networkmanager.c()) {
                                CrashReport crashreport = CrashReport.a(exception, "Ticking memory connection");
                                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Ticking connection");

                                crashreportsystemdetails.a("Connection", new Callable() {
                                    public String a() throws Exception {
                                        return networkmanager.toString();
                                    }

                                    public Object call() throws Exception {
                                        return this.a();
                                    }
                                });
                                throw new ReportedException(crashreport);
                            }

                            ServerConnection.e.warn("Failed to handle packet for " + networkmanager.getSocketAddress(), exception);
                            final ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");

                            networkmanager.a(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener() {
                                public void operationComplete(Future future) throws Exception {
                                    networkmanager.close(chatcomponenttext);
                                }
                            });
                            networkmanager.k();
                        }
                    }
                }
            }

        }
    }

    public MinecraftServer d() {
        return this.f;
    }
}
