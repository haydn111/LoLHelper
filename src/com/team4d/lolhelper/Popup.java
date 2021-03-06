package com.team4d.lolhelper;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team4d.lolhelper.api.APIData;
import com.team4d.lolhelper.api.dto.staticdata.champion.Champion;
import com.team4d.lolhelper.api.dto.staticdata.champion.ChampionSpell;
import com.team4d.lolhelper.api.dto.staticdata.champion.Passive;
import com.team4d.lolhelper.api.dto.staticdata.item.Item;
import com.team4d.lolhelper.api.dto.staticdata.summonerspell.SummonerSpell;

/**
 * Popup class. Handles popups of all types.
 * 
 * @author Alexa
 */
public class Popup
{
	static View layout;

	// Static class
	private Popup()
	{
	}

	/*
	 * ********************************
	 * Loading Popup
	 * ********************************
	 */
	/**
	 * Returns a View for a popup with a loading spinner.
	 * 
	 * @author Alexa
	 * @param activity the current activity
	 * @return a View for a loading popup
	 */
	public static View popupLoading(Activity activity)
	{
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.loadingpopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.popup_loading, view);
		return layout;
	}

	/*
	 * ********************************
	 * Team Builder
	 * ********************************
	 */
	/**
	 * Returns a View for the Team Builder help popup. This is an
	 * instructional popup for how to use Team Builder.
	 * 
	 * @author Alexa
	 * @param activity the current activity
	 * @return a View for the Team Builder help popup.
	 */
	public static View popupTeamBuilder(Activity activity)
	{
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.teambuilderpopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.popup_team_builder, view);
		return layout;
	}

	/*
	 * ********************************
	 * Champion Passive
	 * ********************************
	 */
	/**
	 * Given a champion, creates a View of information for that champion's
	 * passive ability popup.
	 * 
	 * @author Greg
	 * @param activity the current activity
	 * @param champion a Champion
	 * @return a View containing information on the given Champion's passive ability
	 */
	public static View popupChampionPassive(Activity activity, Champion champion)
	{
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.championpassivepopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.popup_champion_passive, view);
		ImageView icon = (ImageView) layout.findViewById(R.id.icon);
		icon.setImageResource(activity.getResources().getIdentifier(
				champion.getName().replaceAll("[^a-zA-Z]+", "").toLowerCase() + 0,
				"drawable", "com.team4d.lolhelper"));

		TextView nameText = (TextView) layout.findViewById(R.id.nameDis);
		TextView effectText = (TextView) layout.findViewById(R.id.effectDis);

		// Setting Text for TextViews
		Passive spell = champion.getPassive();
		nameText.setText(spell.getName());
		effectText.setText("Effect: \n" + spell.getDescription());

		return layout;
	}

	/*
	 * ********************************
	 * Champion Spells
	 * ********************************
	 */
	/**
	 * Given a champion and an ability number, creates a View of information for
	 * that champion's ability popup.
	 * <p>
	 * 1 = Q, 2 = W, 3 = E, 4 = R
	 * 
	 * @author Alexa
	 * @param activity the current activity
	 * @param champion a Champion
	 * @param id the ability number
	 * @return a View containing information on the given Champion's specified ability
	 */
	public static View popupChampionSpell(Activity activity, Champion champion, int id)
	{
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.championspellpopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.popup_champion_spell, view);
		ImageView icon = (ImageView) layout.findViewById(R.id.icon);
		icon.setImageResource(activity.getResources().getIdentifier(
				champion.getName().replaceAll("[^a-zA-Z]+", "").toLowerCase() + id,
				"drawable", "com.team4d.lolhelper"));

		TextView nameText = (TextView) layout.findViewById(R.id.nameDis);
		TextView effectText = (TextView) layout.findViewById(R.id.effectDis);
		TextView cooldownText = (TextView) layout.findViewById(R.id.cooldownDis);
		TextView rangeText = (TextView) layout.findViewById(R.id.rangeDis);

		// Setting Text for TextViews
		ChampionSpell spell = champion.getSpells().get(id-1);
		nameText.setText(spell.getName());
		effectText.setText("Effect: \n" + APIData.parse(spell));
		cooldownText.setText("Cooldown: " + spell.getCooldownBurn());
		rangeText.setText("Range: " + spell.getRangeBurn());
		return layout;
	}

	/*
	 * ********************************
	 * Summoner Spells
	 * ********************************
	 */
	/**
	 * Given the name of a summoner spell, creates a View of information for that spell's popup.
	 * 
	 * @author Alexa
	 * @param activity the current activity
	 * @param name a String of a summoner spell name
	 * @return a View containing information on the specified summoner spell
	 */
	public static View popupSummonerSpell(Activity activity, String name)
	{
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.summonerspellpopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.popup_summoner_spell, view);
		ImageView icon = (ImageView) layout.findViewById(R.id.icon);
		int resID = activity.getResources().getIdentifier(name.replaceAll("[^a-zA-Z]+", "").toLowerCase(),
				"drawable", "com.team4d.lolhelper");
		icon.setImageResource(resID);

		new grabSpell().execute(name);
		return layout;
	}

	/**
	 * Grabs summoner spell info from the API in order to populate the
	 * summoner spell info View.
	 * 
	 * @author Alexa
	 */
	private static class grabSpell extends AsyncTask<String, Void, SummonerSpell>
	{
		@Override
		protected SummonerSpell doInBackground(String... spell_name)
		{
			SummonerSpell c = APIData.getSummonerSpellByName(spell_name[0]);
			// Note: This return value is passed as a parameter to onPostExecute
			return c;
		}

		@Override
		protected void onPostExecute(SummonerSpell spell)
		{
			TextView nameText = (TextView) layout.findViewById(R.id.nameDis);
			TextView effectText = (TextView) layout.findViewById(R.id.effectDis);
			TextView cooldownText = (TextView) layout.findViewById(R.id.cooldownDis);
			TextView rangeText = (TextView) layout.findViewById(R.id.rangeDis);
			TextView levelText = (TextView) layout.findViewById(R.id.levelDis);

			// Setting Text for TextViews
			nameText.setText(spell.getName());
			effectText.setText("Effect: \n" + spell.getDescription());
			cooldownText.setText("Cooldown: \n" + spell.getCooldownBurn());
			rangeText.setText("Range: \n" + spell.getRangeBurn());
			levelText.setText("Level: \n" + spell.getSummonerLevel());
		}
	}

	/*
	 * ********************************
	 * Items
	 * ********************************
	 */
	/**
	 * Given the name of an item, creates a View of information for that item's popup.
	 * 
	 * @author Alexa
	 * @param activity the current activity
	 * @param name a String of an item name
	 * @return a View containing information on the specified item
	 */
	public static View popupItem(Activity activity, String name)
	{
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.itempopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.popup_item, view);
		ImageView icon = (ImageView) layout.findViewById(R.id.icon);
		int resID = activity.getResources().getIdentifier(name.replaceAll("[^a-zA-Z]+", "").toLowerCase(),
				"drawable", "com.team4d.lolhelper");
		icon.setImageResource(resID);

		new grabItem().execute(name);
		return layout;
	}

	/**
	 * Grabs item info from the API in order to populate the
	 * item info View.
	 * 
	 * @author Alexa
	 */
	private static class grabItem extends AsyncTask<String, Void, Item>
	{
		@Override
		protected Item doInBackground(String... name)
		{
			System.out.println(name[0]);
			Item c = APIData.getItemByName(name[0]);
			// Note: This return value is passed as a parameter to onPostExecute
			return c;
		}

		@Override
		protected void onPostExecute(Item item)
		{
			TextView nameText = (TextView) layout.findViewById(R.id.name);
			TextView mapsText = (TextView) layout.findViewById(R.id.maps);
			TextView descriptionText = (TextView) layout.findViewById(R.id.description);
			TextView totalgoldText = (TextView) layout.findViewById(R.id.totalgold);
			TextView sellgoldText = (TextView) layout.findViewById(R.id.sellgold);

			// Setting Text for TextViews
			String str = "";
			if (item.getMaps() != null)
			{
				Map<String, Boolean> maps = item.getMaps();
				// Maps: 1 (SR), 10 (TT), 8 (CS), 12 (HA)
				if (!maps.containsKey("1"))
				{
					str = str + "Summoner's Rift, ";
				}
				if (!maps.containsKey("10"))
				{
					str = str + "Twisted Treeline, ";
				}
				if (!maps.containsKey("8"))
				{
					str = str + "Crystal Scar, ";
				}
				if (!maps.containsKey("12"))
				{
					str = str + "Howling Abyss, ";
				}
				str = str.substring(0, str.length() - 2);
			} else
			{
				str = "All";
			}
			// Get rid of last ", "
			nameText.setText(item.getName());
			mapsText.setText("Maps: \n" + str);
			descriptionText.setText("Description: \n" + APIData.parseOutHtml(item.getDescription()));
			totalgoldText.setText("Total Gold: " + item.getGold().getTotal());
			sellgoldText.setText("Sells For: " + item.getGold().getSell());
		}
	}
}
