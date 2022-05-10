package pl.vertty.nomenhc.scoreboard.packet;

import cn.nukkit.network.protocol.DataPacket;

public class SetObjectivePacket extends DataPacket {

	public String
			displaySlot,
			objectiveName,
			displayName,
			criteriaName;
	public int sortOrder;

	@Override
	public byte pid() {
		return 0x6b;
	}

	@Override
	public void decode() {/**/}

	@Override
	public void encode() {
		this.reset();
		this.putString(this.displaySlot);
		this.putString(this.objectiveName);
		this.putString(this.displayName);
		this.putString(this.criteriaName);
		this.putVarInt(this.sortOrder);
	}
}