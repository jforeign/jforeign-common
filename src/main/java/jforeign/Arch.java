package jforeign;

/**
 * The supported CPU architectures.
 */
public enum Arch {
    /**
     * Unknown Arch
     */
    UNKNOWN(false),
    X86(false),
    X86_64(true),
    ARM(false),
    ARM64(true),
    PPC(false),
    PPC64(true),
    PPC64LE(true),
    S390X(true),
    SPARC(false),
    SPARCV9(true),
    MIPS(false),
    MIPS64(true),
    MIPSEL(false),
    MIPS64EL(true),
    ;
    public static final Arch CURRENT = detectArch();

    private final String archName = name().toLowerCase();
    private final boolean is64bit;

    Arch(boolean is64bit) {
        this.is64bit = is64bit;
    }

    public String getArchName() {
        return archName;
    }

    public boolean is64Bit() {
        return is64bit;
    }

    @Override
    public String toString() {
        return getArchName();
    }

    private static Arch detectArch() {
        String arch = System.getProperty("os.arch").toLowerCase().trim();
        switch (arch) {
            case "x86":
            case "i386":
            case "i486":
            case "i586":
            case "i686":
                return X86;
            case "x64":
            case "x86-64":
            case "amd64":
                return X86_64;
            case "ppc":
            case "powerpc":
                return PPC;
            case "ppc64":
            case "powerpc64":
                if ("little".equals(System.getProperty("sun.cpu.endian"))) {
                    return PPC64LE;
                }
                return PPC64;
            case "ppc64le":
            case "powerpc64le":
                return PPC64LE;
            case "s390":
            case "s390x":
                return S390X;
            case "sparc":
                return SPARC;
            case "sparv9c":
                return SPARCV9;
            case "mips":
                return MIPS;
            case "mips64":
                return MIPS64;
            case "mipsel":
                return MIPSEL;
            case "mips64el":
                return MIPS64EL;
        }

        if (arch.startsWith("aarch64") || arch.startsWith("armv8") || arch.startsWith("arm64")) {
            return ARM64;
        } else if (arch.startsWith("arm")) {
            return ARM;
        }

        return UNKNOWN;
    }
}
