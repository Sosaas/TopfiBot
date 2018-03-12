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

public enum EnglishText {
    INVALID_COMMAND("Unknown Command! Use " + "+" + "help to get an list of all commands."),
    INVALID_PARAM("The Parameters you pass are invalid. Please ensure you have separated all parameters by a single space."),
    MISSING_PERMISSIONS("You don't have the permissions to run this action."),
    DECISION_TIMEOUT_EXPIRED(""),
    COMMAND_SUCCESFUL(""),
    DECISION_MADE(""),
    LOG_REASON("**Reason:**"),
    LOG_RESPONSIBLE("**Responsible:**"),
    LOG_TARGET("**Target:**"),
    LOG_ACTION("**Action:**"),
    MAGIC8BALL_NO_QUESTION("You'll have to ask something if you want me to answer."),
    MAGIC8BALL_FIRST_NO("My answer is no."),
    MAGIC8BALL_SECOND_NO("My sources say it won't."),
    MAGIC8BALL_THIRD_NO("NO"),
    MAGIC8BALl_FOURTH_NO("No way!"),
    MAGIC8BALL_FIFTH_NO("Negative"),
    MAGIC8BALL_FIRST_MIXED("Concentrate and ask again."),
    MAGIC8BALL_SECOND_MIXED("I woudn't bet on it."),
    MAGIC8BALL_THIRD_MIXED("I'm doubting the sense of your question."),
    MAGIC8BALL_FOURTH_MIXED("Just ask again..."),
    MAGIC8BALL_FIFTH_MIXED("Without gurantee."),
    MAGIC8BALL_FIRST_YES("Needless to say"),
    MAGIC8BALL_SECOND_YES("It can't be doubted."),
    MAGIC8BALL_THIRD_YES("Likely"),
    MAGIC8BALL_FOURTH_YES("Under all circumstances!"),
    MAGIC8BALL_FIFTH_YES("My sources say so."),
    MAGIC8BALL_SIXTH_YES("The omens are favourable"),
    MAGIC8BALL_SEVENTH_YES("A clear yes from me."),
    MAGIC8BALL_EIGHTLY_YES("YES"),
    MAGIC8BALL_NINTH_YES("You can bet on it!"),
    MAGIC8BALL_TENTH_YES("Defintley yes!"),
    MEMBER_BANNED("Banned member!"),
    MEMBER_UNBANNED("Unbanned member"),
    MEMBER_KICKED("Kicked member!"),
    GUILD_PRUNED("Inactive Members were removed from your Server!"),
    INFO_JOINLINK("Use the link below to bring Topfi Bot to your Server as well:"),
    BOT_LACK_OF_PERMISSION(""),
    MEMBER_NOT_FOUND("I couldn't found a member with the name you pass."),
    EDITING_HIHGER_ROLE("You can't administrate members with a higher role."),
    BAN("Ban"),
    BAN_ADDITION1(""),
    BAN_ADDITION2(""),
    TOPFI("");
    
    private String text;
    
    private EnglishText(String toText) {
	text = toText;
    }
    public String getText() {
	return this.text;
    }
}
