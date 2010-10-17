package com.nullprogram.chess.pieces;

import java.util.WeakHashMap;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * Serves cached images of requsted size.
 *
 * This will cache the recent requests so it's not hitting the disk
 * every time the display needs an image.
 */
public class ImageServer {

    /**
     * Hidden constructor.
     */
    protected ImageServer() {
    }

    /** The image cache. */
    private static WeakHashMap<CacheKey, BufferedImage> cache
    = new WeakHashMap<CacheKey, BufferedImage>();

    /**
     * A key to an image in the cache.
     */
    private static final class CacheKey {

        /** File name of the image. */
        private String filename;

        /** Requested size of the image. */
        private int size;

        /** Seed for the hashCode() function. */
        static final int SEED = 0xc54c8016;

        /**
         * Create a new key with given name and size.
         *
         * @param name    filename
         * @param reqSize requested size
         */
        private CacheKey(final String name, final int reqSize) {
            filename = name;
            size = reqSize;
        }

        /** {@inheritDoc} */
        public int hashCode() {
            return filename.hashCode()
                   ^ (SEED * (size << (size % (2 * 2))));
        }

        /** {@inheritDoc} */
        public boolean equals(final Object that) {
            if (this == that) {
                return true;
            }
            if (!(that instanceof CacheKey)) {
                return false;
            }
            CacheKey key = (CacheKey) that;
            return filename.equals(key.filename) && (size == key.size);
        }
    }

    /**
     * Return named image scaled to given size.
     *
     * @param name name of the image
     * @param size size of the returned image
     * @return     the requested image
     */
    public static BufferedImage getTile(final String name, final int size) {
        CacheKey key = new CacheKey(name, size);
        BufferedImage cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        BufferedImage orig = null, image = null;
        String prefix = "/com/nullprogram/chess/pieces/";
        String file = prefix + name + ".png";
        try {
            orig = ImageIO.read(ImageServer.class.getResource(file));
            image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                               RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(orig, 0, 0, size, size, null);
            g.dispose();
        } catch (java.io.IOException e) {
            String message = "Failed to read image: " + file;
            System.out.println(message);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            String message = "Failed to find image: " + file;
            System.out.println(message);
            System.exit(1);
        }
        cache.put(key, image);
        return image;
    }
}
