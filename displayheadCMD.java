
import net.contaria.bungeesystem.image.ImageChar;
import net.contaria.bungeesystem.image.ImageMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class displayheadCMD extends Command {

    public final HashMap<String, ImageMessage> head_cache = new HashMap<>(  );
    
    public displayheadCMD( String name ) {
        super( name );
    }

    @Override
    public void execute( CommandSender commandSender, String[] args ) {
        if( !( commandSender instanceof ProxiedPlayer ) ) {
            commandSender.sendMessage( "Du musst ein Spieler sein" );
            return;
        }
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        
        if(!player.hasPermission( "system.displayhead" )) {
            player.sendMessage( "Keine Rechte" );
            return;
        }

        if(args.length != 1) {
            player.sendMessage( "/displayhead <Spielername>" );
            return;
        }
        final String playerName = args[0];
        ImageMessage imageMessage = null;
        
        if(!head_cache.containsKey( playerName )) {
            try {
                imageMessage = new ImageMessage( ImageIO.read( new URL(
                        "https://minotar.net/avatar/" + playerName + "/8.png" ) ), 8, ImageChar.BLOCK.getChar( ) );
            } catch ( IOException ignored ) { }

            if ( imageMessage == null ) {
                commandSender.sendMessage( "Es ist ein Fehler unterlaufen" );
                return;
            }
            
            head_cache.put( playerName, imageMessage );
        } else {
            imageMessage = head_cache.get( playerName );
        }
        
        for ( String lines : imageMessage.getLines() ) {
            player.sendMessage( lines );
        }
        player.sendMessage( "Command erfolgreich ausgeführt" );
    }
}
