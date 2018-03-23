/* Copyright 2018 Jonas Wischnewski

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package botcore.languages;

public enum GermanText {
    INVALID_COMMAND("Unbekannter Befehl! Benutze " + "+" + "help für eine Auflistung aller gültiger Befehle."),
    INVALID_PARAM("Ungültige Parameter! Stelle sicher, dass du alle Parameter durch Leerzeichen getrennt hat."),
    MISSING_PERMISSIONS("Dir fehlen die nötigen Berechtigungen, um diese Aktion hier auszuführen."),
    DECISION_TIMEOUT_EXPIRED("Du hast dich nicht oder zu spät entschieden. Versuche es bitte noch einmal."),
    COMMAND_SUCCESFUL(""),
    DECISION_MADE(""),
    LOG_REASON("**Grund:**"),
    LOG_RESPONSIBLE("**Verantwortlich:**"),
    LOG_TARGET("**Betroffen:**"),
    LOG_ACTION("**Aktion:**"),
    MAGIC8BALL_NO_QUESTION("Du musst schon etwas fragen."),
    MAGIC8BALL_FIRST_NO("Meine Antwort ist Nein."),
    MAGIC8BALL_SECOND_NO("Meine Quellen verneinen dies."),
    MAGIC8BALL_THIRD_NO("NEIN"),
    MAGIC8BALL_FOURTH_NO("Auf keinen Fall!"),
    MAGIC8BALL_FIFTH_NO("Negativ."),
    MAGIC8BALL_FIRST_MIXED("Konzentriere dich und frag nochmal."),
    MAGIC8BALL_SECOND_MIXED("Darauf würde ich nicht wetten."),
    MAGIC8BALL_THIRD_MIXED("Ich bezweifle den Sinn dieser Frage."),
    MAGIC8BALL_FOURTH_MIXED("Frag doch einfach nochmal."),
    MAGIC8BALL_FIFTH_MIXED("Aber ohne Garantie..."),
    MAGIC8BALL_FIRST_YES("Selbstverständlich!"),
    MAGIC8BALL_SECOND_YES("Daran lässt sich nicht zweifeln."),
    MAGIC8BALL_THIRD_YES("Wahrscheinlich Ja."),
    MAGIC8BALL_FOURTH_YES("Unbedingt."),
    MAGIC8BALL_FIFTH_YES("Laut meinen Quellen schon."),
    MAGIC8BALL_SIXTH_YES("Die Sterne stehen jedenfalls günstig dafür."),
    MAGIC8BALL_SEVENTH_YES("Ein klares Ja."),
    MAGIC8BALL_EIGHTLY_YES("JA"),
    MAGIC8BALL_NINTH_YES("Worauf du wetten kannst!"),
    MAGIC8BALL_TENTH_YES("Auf jeden Fall!"),
    MEMBER_BANNED("Mitglied gebannt!"),
    MEMBER_UNBANNED("User entbannt!"),
    MEMBER_KICKED("Mitglied gekickt!"),
    GUILD_PRUNED("Die inaktiven Member wurden gekickt."),
    INFO_JOINLINK("Benutze diesen Link, um Topfi Bot auch auf deinen Server zu bringen:"),
    BOT_LACK_OF_PERMISSION("Dem Bot fehlen die Berechtigungen, um diesen Befehl auszuführen. Stelle sicher, dass der Bot eine passende Rolle hat."),
    MEMBER_NOT_FOUND("Es konnte kein Mitglied mit diesem Namen gefunden werden."),
    EDITING_HIHGER_ROLE("Du kannst keine Mitglieder verwalten die eine höhere Rolle innehaben."),
    BAN("Bann"),
    BAN_ADDITION1("/ Nachrichten der letzten"),
    BAN_ADDITION2(" Tage gelöscht"),
    TOPFI("");
    
    private String text;
    
    private GermanText(String toText) {
	text = toText;
    }
    
    public String getText() {
	return this.text;
    }
}
