PaulBGD's ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Theme:__ What random events can occur in Minecraft?
- __Time:__ Time 3 (7/12/2014 14:00 to 7/13/2014 00:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ https://twitch.tv/PaulBGD

<!-- put chosen theme above -->

---------------------------------------

Compilation
-----------

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/tenjava/PaulBGD-t3`
- Compile and create the plugin package using Maven: `mvn`

Maven will download all required dependencies and build a ready-for-use plugin package!

---------------------------------------

Usage
-----

1. Install plugin by dropping it into your plugins folder. Need more help? Check [this](https://www.youtube.com/watch?v=TR7rH8G0sas) out!
2. When you head in game, you may notice stuff starting to happen.. like tornados, floods, and mudslides! Nothing is safe!
3. If you want to purposely cause one of these (trolling =3) just type /disaster! You'll be destroying everything in no time.

Permissions
-----
- naturaldisasters.menu - Opens the Natural Disasters menu

- naturaldisasters.tornado - Allows you to create a tornado
- naturaldisasters.mudslide - Allows you to create a mudslide
- naturaldisasters.flood - Allows you to create a flood

API
----
First check if you can create the disaster with
``NaturalDisaster.canCreateDisaster(Disaster.XXXX, block)``
After you know you can, apply the disaster with
``NaturalDisaster.createDisaster(Disaster.XXXX, block)``
