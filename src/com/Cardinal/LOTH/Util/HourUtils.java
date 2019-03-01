package com.Cardinal.LOTH.Util;

import java.time.LocalTime;

import com.Cardinal.LOTH.Util.WebUtils.WebLang;

public class HourUtils {
	public static Hour getCurrentHour() {
		return getHour(LocalTime.now());
	}

	public static Hour getHour(LocalTime time) {
		int hr = time.getHour() + (time.getMinute() > 30 ? 0 : -1);
		return Hour.at(hr == -1 ? 0 : hr);
	}

	public enum Hour {
		Matutinum, Laudes, Prima, Tertia, Sexta, Nona, Vesperae, Completorium;
		public static Hour at(int militaryHour) {
			if (militaryHour > 23)
				throw new IndexOutOfBoundsException(militaryHour + " is greater than 23.");
			if (militaryHour < 0)
				throw new IndexOutOfBoundsException(militaryHour + " is less than 0.");
			switch (militaryHour) {
			case 23:
				return Matutinum;
			case 0:
				return Matutinum;
			case 1:
				return Matutinum;
			case 2:
				return Laudes;
			case 3:
				return Laudes;
			case 4:
				return Laudes;
			case 5:
				return Prima;
			case 6:
				return Prima;
			case 7:
				return Prima;
			case 8:
				return Tertia;
			case 9:
				return Tertia;
			case 10:
				return Tertia;
			case 11:
				return Sexta;
			case 12:
				return Sexta;
			case 13:
				return Sexta;
			case 14:
				return Nona;
			case 15:
				return Nona;
			case 16:
				return Nona;
			case 17:
				return Vesperae;
			case 18:
				return Vesperae;
			case 19:
				return Vesperae;
			case 20:
				return Completorium;
			case 21:
				return Completorium;
			case 22:
				return Completorium;
			default:
				return null;
			}
		}

		public LocalTime time() {
			switch (this) {
			case Matutinum:
				return LocalTime.MIDNIGHT;
			case Laudes:
				return LocalTime.of(3, 0);
			case Prima:
				return LocalTime.of(6, 0);
			case Tertia:
				return LocalTime.of(9, 0);
			case Sexta:
				return LocalTime.of(12, 0);
			case Nona:
				return LocalTime.of(15, 0);
			case Vesperae:
				return LocalTime.of(18, 0);
			case Completorium:
				return LocalTime.of(21, 0);
			default:
				return null;
			}
		}

		public String toString(WebLang lang) {
			if (lang.equals(WebLang.LAT))
				return this.equals(Vesperae) ? "Vespera" : toString();
			else
				switch (this) {
				case Matutinum:
					return "Matins";
				case Laudes:
					return toString();
				case Prima:
					return "Prime";
				case Tertia:
					return "Terce";
				case Sexta:
					return "Sext";
				case Nona:
					return "Nones";
				case Vesperae:
					return "Vespers";
				case Completorium:
					return "Compline";
				default:
					return toString();
				}

		}
	}
}
