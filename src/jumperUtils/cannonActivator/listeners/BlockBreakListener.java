package jumperutils.cannonactivator.listeners;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import jumperutils.cannonactivator.CannonActivator;
import jumperutils.cannonactivator.utils.ButtonData;

public class BlockBreakListener implements Listener{
	public HashMap<Player, ButtonData> blockdata = new HashMap<>();
	public final CannonActivator cannonActivator;
	
	public BlockBreakListener(CannonActivator cannonActivator) {
		this.cannonActivator = cannonActivator;
		cannonActivator.main.getServer().getPluginManager().registerEvents(this, cannonActivator.main);
	}
	
	@EventHandler
	public void onBlockBroken(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p == null) {
			System.out.println("player in BlockBreakListener was null (what the heck?!)");
			return;
		}
		Material item = p.getInventory().getItemInMainHand().getType();
		if(item == null) {
			System.out.println("item in playerhand of BlockBreakListener was null");
			return;
		}
		if(!item.equals(Material.DIAMOND_HOE)) {
			return;
		}
		e.setCancelled(true);
		playerSelectsBlock(p, e.getBlock());
	}
	
	public void playerSelectsBlock(Player p, Block block) {
		if(block == null || block.getType() == Material.AIR) {
			p.sendMessage("�cCouldn't select Block, might be too far away.");
			return;
		}
		if(blockdata.containsKey(p)) {
			blockdata.get(p).setLocation(block.getWorld(), block.getX(), block.getY(), block.getZ());
		}else {
			blockdata.put(p, new ButtonData(block.getWorld(), block.getX(), block.getY(), block.getZ()));
		}
		p.sendMessage("�aSelected Block at location �b"+block.getX()+", "+block.getY()+", "+block.getZ()+" �a!");
	}
}
