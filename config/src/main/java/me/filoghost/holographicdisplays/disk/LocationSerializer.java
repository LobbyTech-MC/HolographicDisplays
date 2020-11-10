/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.filoghost.holographicdisplays.disk;

import me.filoghost.holographicdisplays.exception.InvalidFormatException;
import me.filoghost.holographicdisplays.exception.WorldNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LocationSerializer {
    
    private static DecimalFormat numberFormat = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.ROOT));

    public static Location locationFromString(String input) throws WorldNotFoundException, InvalidFormatException {
        if (input == null) {
            throw new InvalidFormatException();
        }
        
        String[] parts = input.split(",");
        
        if (parts.length != 4) {
            throw new InvalidFormatException();
        }
        
        try {
            double x = Double.parseDouble(parts[1].replace(" ", ""));
            double y = Double.parseDouble(parts[2].replace(" ", ""));
            double z = Double.parseDouble(parts[3].replace(" ", ""));
        
            World world = Bukkit.getWorld(parts[0].trim());
            if (world == null) {
                throw new WorldNotFoundException(parts[0].trim());
            }
            
            return new Location(world, x, y, z);
            
        } catch (NumberFormatException ex) {
            throw new InvalidFormatException();
        }
    }
    
    public static String locationToString(Location loc) {
        return (loc.getWorld().getName() + ", " + numberFormat.format(loc.getX()) + ", " + numberFormat.format(loc.getY()) + ", " + numberFormat.format(loc.getZ()));
    }
}