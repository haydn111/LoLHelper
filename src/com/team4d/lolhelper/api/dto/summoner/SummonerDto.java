package com.team4d.lolhelper.api.dto.summoner;

public class SummonerDto
{
	private long id; // Summoner ID.
	private String name; // Summoner name.
	private int profileIconId; // ID of the summoner icon associated with the summoner.
	private long revisionDate; // Date summoner was last modified specified as epoch milliseconds.
	private long summonerLevel; // Summoner level associated with the summoner.

	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the profileIconId
	 */
	public int getProfileIconId()
	{
		return profileIconId;
	}

	public void setProfileIconId(int profileIconId)
	{
		this.profileIconId = profileIconId;
	}

	/**
	 * @return the revisionDate
	 */
	public long getRevisionDate()
	{
		return revisionDate;
	}

	public void setRevisionDate(long revisionDate)
	{
		this.revisionDate = revisionDate;
	}

	/**
	 * @return the summonerLevel
	 */
	public long getSummonerLevel()
	{
		return summonerLevel;
	}

	public void setSummonerLevel(long summonerLevel)
	{
		this.summonerLevel = summonerLevel;
	}
}
